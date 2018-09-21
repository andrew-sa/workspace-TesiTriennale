package manager.data.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import exceptions.TupleNotFoundException;
import manager.connection.DriverManagerConnectionPool;
import manager.data.model.GDPPerCapitaData;
import manager.data.model.PopulationData;
import manager.data.model.PovertyData;

public class BoundaryValueDAO {

	public BoundaryValueDAO() {
		// TODO Auto-generated constructor stub
	}
	
	public void save(String dataType) throws SQLException
	{
		switch (dataType)
		{
			case PopulationData.DATA_TYPE:
			{
				double[] boundaryValues = readMinAndMax(dataType);
				saveMinAndMax(dataType, boundaryValues);
				break;
			}
			case PovertyData.DATA_TYPE:
			{
				double[] boundaryValues = readMinAndMax(dataType);
				saveMinAndMax(dataType, boundaryValues);
				break;
			}
			case GDPPerCapitaData.DATA_TYPE:
			{
				double[] boundaryValues = readMinAndMax(dataType);
				saveMinAndMax(dataType, boundaryValues);
				break;
			}
			default:
				break;
		}
	}
	
	private double[] readMinAndMax(String dataType) throws SQLException
	{
		double[] boundaryValues = new double[2];
		Connection con = DriverManagerConnectionPool.getInstance().getConnection();
		Statement stm = con.createStatement();
		switch (dataType)
		{
			case PopulationData.DATA_TYPE:
			{
				ResultSet rs = stm.executeQuery(READ_BOUNDARIES_POPULATION);
				if (rs.next())
				{
					boundaryValues[0] = rs.getDouble(1);
					boundaryValues[1] = rs.getDouble(2);
				}
				break;
			}
			case PovertyData.DATA_TYPE:
			{
				ResultSet rs = stm.executeQuery(READ_BOUNDARIES_POVERTY);
				if (rs.next())
				{
					boundaryValues[0] = rs.getDouble(1);
					boundaryValues[1] = rs.getDouble(2);
				}
				break;
			}
			case GDPPerCapitaData.DATA_TYPE:
			{
				ResultSet rs = stm.executeQuery(READ_BOUNDARIES_GDP_PER_CAPITA);
				if (rs.next())
				{
					boundaryValues[0] = rs.getDouble(1);
					boundaryValues[1] = rs.getDouble(2);
				}
				break;
			}
			default:
				break;
		}
		con.commit();
		stm.close();
		DriverManagerConnectionPool.getInstance().releaseConnection(con);
		return boundaryValues;
	}
	
	private void saveMinAndMax(String dataType, double[] boundaryValues) throws SQLException
	{
		Connection con = DriverManagerConnectionPool.getInstance().getConnection();
		PreparedStatement ps = con.prepareStatement(CREATE);
		
		ps.setString(1, MIN_VALUE);
		ps.setString(2, dataType);
		ps.setDouble(3, boundaryValues[0]);
		ps.executeUpdate();
		
		ps.setString(1, MAX_VALUE);
		ps.setString(2, dataType);
		ps.setDouble(3, boundaryValues[1]);
		ps.executeUpdate();
		
		con.commit();
		ps.close();
		DriverManagerConnectionPool.getInstance().releaseConnection(con);
	}
	
	public double readMinForDataType(String dataType) throws SQLException, TupleNotFoundException
	{
		double value = Double.NaN;
		Connection con = DriverManagerConnectionPool.getInstance().getConnection();
		PreparedStatement ps = con.prepareStatement(READ);
		ps.setString(1, MIN_VALUE);
		ps.setString(2, dataType);
		ResultSet rs = ps.executeQuery();
		if (rs.next())
		{
			value = rs.getDouble(1);
		}
		con.commit();
		rs.close();
		ps.close();
		DriverManagerConnectionPool.getInstance().releaseConnection(con);
		if (!Double.isNaN(value))
		{
			return value;
		}
		else
		{
			throw new TupleNotFoundException();
		}
	}
	
	public double readMaxForDataType(String dataType) throws SQLException, TupleNotFoundException
	{
		double value = Double.NaN;
		Connection con = DriverManagerConnectionPool.getInstance().getConnection();
		PreparedStatement ps = con.prepareStatement(READ);
		ps.setString(1, MAX_VALUE);
		ps.setString(2, dataType);
		ResultSet rs = ps.executeQuery();
		if (rs.next())
		{
			value = rs.getDouble(1);
		}
		con.commit();
		rs.close();
		ps.close();
		DriverManagerConnectionPool.getInstance().releaseConnection(con);
		if (!Double.isNaN(value))
		{
			return value;
		}
		else
		{
			throw new TupleNotFoundException();
		}
	}
	
	public void delete(String dataType) throws SQLException
	{
		Connection con = DriverManagerConnectionPool.getInstance().getConnection();
		PreparedStatement ps = con.prepareStatement(DELETE);
		ps.setString(1, dataType);
		ps.executeUpdate();
		con.commit();
		ps.close();
		DriverManagerConnectionPool.getInstance().releaseConnection(con);
	}
	
//	public static void main(String[] args) throws SQLException {
//		BoundaryValueDAO dao = new BoundaryValueDAO();
//		dao.save(PopulationData.DATA_TYPE);
//	}
	
	private static final String MIN_VALUE = "MIN";
	private static final String MAX_VALUE = "MAX";
	
	private static final String CREATE = "INSERT INTO boundaryvalue VALUES (?, ?, ?)";
	private static final String READ_BOUNDARIES_POPULATION = "SELECT MIN(pop.Value), MAX(pop.Value) FROM countrypopulation pop, countrypoverty pov WHERE pop.Year >= 1995 AND pop.Country = pov.Country AND pop.Year = pov.Year";
	private static final String READ_BOUNDARIES_POVERTY = "SELECT MIN(Value), MAX(Value) FROM countrypoverty WHERE Year >= 1995";
	private static final String READ_BOUNDARIES_GDP_PER_CAPITA = "SELECT MIN(gdp.Value), MAX(gdp.Value) FROM countrygdppercapita gdp, countrypoverty pov WHERE gdp.Year >= 1995 AND gdp.Country = pov.Country AND gdp.Year = pov.Year";
	private static final String READ = "SELECT Value FROM boundaryvalue WHERE BoundaryType = ? AND DataType = ?";
	private static final String DELETE = "DELETE FROM boundaryvalue WHERE DataType = ?";

}
