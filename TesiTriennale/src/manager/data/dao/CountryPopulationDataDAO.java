package manager.data.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Logger;

import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;

import exceptions.TupleNotFoundException;
import manager.connection.DriverManagerConnectionPool;
import manager.data.model.Country;
import manager.data.model.CountryPopulationData;

public class CountryPopulationDataDAO {

	public CountryPopulationDataDAO() {
		// TODO Auto-generated constructor stub
	}
	
	public void save(ArrayList<CountryPopulationData> data) throws SQLException
	{
		Connection con = DriverManagerConnectionPool.getInstance().getConnection();
		PreparedStatement ps = con.prepareStatement(CREATE);
		for (CountryPopulationData pd: data)
		{
			ps.setString(1, pd.getName());
			ps.setString(2, pd.getYear());
			ps.setDouble(3, pd.getValue());
			ps.setString(4, pd.getSource());
			try
			{
//				System.out.println("SAVING: " + pd);
				LOGGER.info("SAVING: " + pd);
				ps.executeUpdate();
			}
			catch (MySQLIntegrityConstraintViolationException e)
			{
				LOGGER.warning("Unable to save " + pd + " in the database");
//				System.err.println("Unable to save the Population data [" + pd.getName() + ", " + pd.getYear() + "] in the database");
				System.out.println(e.getMessage() + "\n");
//				e.printStackTrace();
			}
			con.commit();
		}
		ps.close();
		DriverManagerConnectionPool.getInstance().releaseConnection(con);
	}
	
	public ArrayList<CountryPopulationData> read(Country country) throws SQLException
	{
		ArrayList<CountryPopulationData> data = new ArrayList<>();
		Connection con = DriverManagerConnectionPool.getInstance().getConnection();
		PreparedStatement ps = con.prepareStatement(READ);
		ps.setString(1, country.getCode());
		ResultSet rs = ps.executeQuery();
		while (rs.next())
		{
			data.add(new CountryPopulationData(rs.getString(1), rs.getString(2), rs.getDouble(3)));
		}
		con.commit();
		rs.close();
		ps.close();
		DriverManagerConnectionPool.getInstance().releaseConnection(con);
		return data;
	}
	
	public ArrayList<CountryPopulationData> readByRegion(String region) throws SQLException
	{
		ArrayList<CountryPopulationData> data = new ArrayList<>();
		Connection con = DriverManagerConnectionPool.getInstance().getConnection();
		PreparedStatement ps = con.prepareStatement(READ_BY_REGION);
		ps.setString(1, region);
		ResultSet rs = ps.executeQuery();
		while (rs.next())
		{
			data.add(new CountryPopulationData(rs.getString(1), rs.getString(2), rs.getDouble(3)));
		}
		con.commit();
		rs.close();
		ps.close();
		DriverManagerConnectionPool.getInstance().releaseConnection(con);
		return data;
	}
	
	public double readValueByCountryAndYear(Country country, String year) throws SQLException, TupleNotFoundException
	{
		double value = Double.NaN;
		Connection con = DriverManagerConnectionPool.getInstance().getConnection();
		PreparedStatement ps = con.prepareStatement(READ_VALUE_BY_COUNTRY_AND_YEAR);
		ps.setString(1, country.getCode());
		ps.setString(2, year);
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

	public void delete(String source) throws SQLException
	{
		Connection con = DriverManagerConnectionPool.getInstance().getConnection();
		PreparedStatement ps = con.prepareStatement(DELETE);
		ps.setString(1, source);
		ps.executeUpdate();
		con.commit();
		ps.close();
		DriverManagerConnectionPool.getInstance().releaseConnection(con);
	}
	
//	private static final Logger LOGGER = Logger.getLogger(PopulationData.DATA_TYPE);
	private static final Logger LOGGER = Logger.getLogger("updating");
	
	private static final String CREATE = "INSERT INTO countrypopulation VALUES (?, ?, ?, ?)";
	private static final String READ = "SELECT * FROM countrypopulation WHERE Country = ?";
	private static final String READ_BY_REGION = "SELECT cp.Country, cp.Year, cp.Value FROM country c, countrypopulation cp WHERE c.Code = cp.Country AND c.Region = ?";
	private static final String READ_VALUE_BY_COUNTRY_AND_YEAR = "SELECT Value FROM countrypopulation WHERE Country = ? AND Year = ?";
	private static final String DELETE = "DELETE FROM countrypopulation WHERE Source = ?";
	
}
