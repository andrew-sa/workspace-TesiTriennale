package manager.data.dao;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Logger;

import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;

import exceptions.TupleNotFoundException;
import manager.connection.DriverManagerConnectionPool;
import manager.data.model.Country;
import manager.data.model.CountryPopulationData;
import servlets.SaveDataManualInsertion;

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
			ps.setBoolean(5, pd.isCalculated());
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
	
	public void save(ArrayList<CountryPopulationData> data, PrintWriter printWriter) throws SQLException
	{
		Connection con = DriverManagerConnectionPool.getInstance().getConnection();
		PreparedStatement ps = con.prepareStatement(CREATE);
		for (CountryPopulationData pd: data)
		{
			ps.setString(1, pd.getName());
			ps.setString(2, pd.getYear());
			ps.setDouble(3, pd.getValue());
			ps.setString(4, pd.getSource());
			ps.setBoolean(5, pd.isCalculated());
			try
			{
//				System.out.println("SAVING: " + pd);
				LOGGER.info("SAVING: " + pd);
				printWriter.println("SAVING: " + pd);
				printWriter.flush();
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
	
	public void saveManualInsertion(CountryPopulationData data, PrintWriter printWriter) throws SQLException
	{
		LOGGER.info("SAVING: " + data);
		printWriter.print("SAVING: " + data);
		printWriter.print("<br>");
		
		Connection con = DriverManagerConnectionPool.getInstance().getConnection();
		PreparedStatement ps = con.prepareStatement(READ_SOURCE_BY_COUNTRY_AND_YEAR);
		ps.setString(1, data.getName());
		ps.setString(2, data.getYear());
		ResultSet rs = ps.executeQuery();
		if (rs.next())
		{
			String source = rs.getString(1);
			if (source.equals(SaveDataManualInsertion.SOURCE))
			{
				con.commit();
				rs.close();
				ps.close();
				
				ps = con.prepareStatement(UPDATE);
				ps.setDouble(1, data.getValue());
				ps.setString(2, data.getName());
				ps.setString(3, data.getYear());
				
				try
				{
					int result = ps.executeUpdate();
					if (result == 0)
					{
						LOGGER.warning("Unable to update " + data + " in the database");
						printWriter.print("DATA NOT UPDATED");
						printWriter.print("<br>");
						printWriter.flush();
					}
					else
					{
						LOGGER.info("Updated " + data + " in the database");
						printWriter.print("DATA UPDATED");
						printWriter.print("<br>");
						printWriter.flush();
					}
				}
				catch (MySQLIntegrityConstraintViolationException e)
				{
					LOGGER.warning("Invalid country for: " + data);
					printWriter.print("SAVE FAILED BECAUSE THE COUNTRY-CODE IS NOT VALID");
					printWriter.print("<br>");
					printWriter.flush();
				}
				
				con.commit();
				ps.close();
			}
			else
			{
				con.commit();
				rs.close();
				ps.close();
				
				LOGGER.warning("Unable to update " + data + " in the database because DATA FROM A DIFFERENT SOURCE CAN NOT BE MANUALLY UPDATED");
				printWriter.print("DATA FROM A DIFFERENT SOURCE CAN NOT BE MANUALLY UPDATED");
				printWriter.print("<br>");
				printWriter.flush();
			}
		}
		else
		{
			con.commit();
			rs.close();
			ps.close();
			
			ps = con.prepareStatement(CREATE);
			ps.setString(1, data.getName());
			ps.setString(2, data.getYear());
			ps.setDouble(3, data.getValue());
			ps.setString(4, data.getSource());
			ps.setBoolean(5, data.isCalculated());
			
			try
			{
				int result = ps.executeUpdate();
				if (result == 0)
				{
					LOGGER.warning("Unable to save " + data + " in the database");
					printWriter.print("DATA NOT SAVED");
					printWriter.print("<br>");
					printWriter.flush();
				}
				else
				{
					LOGGER.warning("Saved " + data + " in the database");
					printWriter.print("DATA SAVED");
					printWriter.print("<br>");
					printWriter.flush();
				}
			}
			catch (MySQLIntegrityConstraintViolationException e)
			{
				LOGGER.warning("Invalid country for: " + data);
				printWriter.print("SAVE FAILED BECAUSE THE COUNTRY-CODE IS NOT VALID");
				printWriter.print("<br>");
				printWriter.flush();
			}
			con.commit();
			ps.close();
		}
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
	
	public ArrayList<CountryPopulationData> readManualInsertions() throws SQLException
	{
		ArrayList<CountryPopulationData> data = new ArrayList<>();
		Connection con = DriverManagerConnectionPool.getInstance().getConnection();
		Statement stm = con.createStatement();
		ResultSet rs = stm.executeQuery(READ_MANUAL_INSERTIONS);
		while (rs.next())
		{
			data.add(new CountryPopulationData(rs.getString(1), rs.getString(2), rs.getDouble(3)));
		}
		con.commit();
		rs.close();
		DriverManagerConnectionPool.getInstance().releaseConnection(con);
		return data;
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
	
	public void delete(CountryPopulationData data, PrintWriter printWriter) throws SQLException
	{
		LOGGER.info("DELETING: " + data.shortToString());
		printWriter.print("DELETING: " + data.shortToString());
		printWriter.print("<br>");
		
		Connection con = DriverManagerConnectionPool.getInstance().getConnection();
		PreparedStatement ps = con.prepareStatement(DELETE_INSERTED_DATA);
		ps.setString(1, data.getName());
		ps.setString(2, data.getYear());
		int result = ps.executeUpdate();
		if (result == 1)
		{
			LOGGER.info("Deleted " + data.shortToString() + " in the database");
			printWriter.print("DATA DELETED");
			printWriter.print("<br>");
			printWriter.flush();
		}
		else
		{
			LOGGER.warning("Unable to delete " + data.shortToString() + " in the database");
			printWriter.print("DATA NOT DELETED");
			printWriter.print("<br>");
			printWriter.flush();
		}
		con.commit();
		ps.close();
		DriverManagerConnectionPool.getInstance().releaseConnection(con);
	}
	
//	private static final Logger LOGGER = Logger.getLogger(PopulationData.DATA_TYPE);
	private static final Logger LOGGER = Logger.getLogger("updating");
	
	private static final String CREATE = "INSERT INTO countrypopulation VALUES (?, ?, ?, ?, ?)";
	private static final String READ_SOURCE_BY_COUNTRY_AND_YEAR = "SELECT Source FROM countrypopulation WHERE Country = ? AND Year = ?";
	private static final String UPDATE = "UPDATE countrypopulation SET Value = ? WHERE Country = ? AND Year = ?";
	private static final String READ = "SELECT * FROM countrypopulation WHERE Country = ?";
	private static final String READ_BY_REGION = "SELECT cp.Country, cp.Year, cp.Value FROM country c, countrypopulation cp WHERE c.Code = cp.Country AND c.Region = ?";
	private static final String READ_VALUE_BY_COUNTRY_AND_YEAR = "SELECT Value FROM countrypopulation WHERE Country = ? AND Year = ?";
	private static final String READ_MANUAL_INSERTIONS = "SELECT Country, Year, Value FROM countrypopulation WHERE Source = 'none'";
	private static final String DELETE = "DELETE FROM countrypopulation WHERE Source = ?";
	private static final String DELETE_INSERTED_DATA = "DELETE FROM countrypopulation WHERE Country = ? AND Year = ? AND Source = 'none'";
	
}
