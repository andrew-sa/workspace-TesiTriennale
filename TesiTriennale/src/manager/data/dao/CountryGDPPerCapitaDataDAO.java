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
import manager.data.model.CountryGDPPerCapitaData;
import servlets.SaveDataManualInsertion;

public class CountryGDPPerCapitaDataDAO {

	public CountryGDPPerCapitaDataDAO() {
		// TODO Auto-generated constructor stub
	}
	
	public void save(ArrayList<CountryGDPPerCapitaData> data) throws SQLException
	{
		Connection con = DriverManagerConnectionPool.getInstance().getConnection();
		PreparedStatement ps = con.prepareStatement(CREATE);
		for (CountryGDPPerCapitaData gdp: data)
		{
			ps.setString(1, gdp.getName());
			ps.setString(2, gdp.getYear());
			ps.setDouble(3, gdp.getValue());
			ps.setString(4, gdp.getSource());
			ps.setBoolean(5, gdp.isCalculated());
			try
			{
				LOGGER.info("SAVING: " + gdp);
				ps.executeUpdate();
			}
			catch (MySQLIntegrityConstraintViolationException e)
			{
				LOGGER.warning("Unable to save " + gdp + " in the database");
//				System.err.println("Unable to save the GDP per capita data [" + gdp.getName() + ", " + gdp.getYear() + "] in the database");
				System.out.println(e.getMessage() + "\n");
//				e.printStackTrace();
			}
			con.commit();
		}
		ps.close();
		DriverManagerConnectionPool.getInstance().releaseConnection(con);
	}
	
	public void save(ArrayList<CountryGDPPerCapitaData> data, PrintWriter printWriter) throws SQLException
	{
		Connection con = DriverManagerConnectionPool.getInstance().getConnection();
		PreparedStatement ps = con.prepareStatement(CREATE);
		for (CountryGDPPerCapitaData gdp: data)
		{
			ps.setString(1, gdp.getName());
			ps.setString(2, gdp.getYear());
			ps.setDouble(3, gdp.getValue());
			ps.setString(4, gdp.getSource());
			ps.setBoolean(5, gdp.isCalculated());
			try
			{
				LOGGER.info("SAVING: " + gdp);
				printWriter.println("SAVING: " + gdp);
				printWriter.flush();
				ps.executeUpdate();
			}
			catch (MySQLIntegrityConstraintViolationException e)
			{
				LOGGER.warning("Unable to save " + gdp + " in the database");
//				System.err.println("Unable to save the GDP per capita data [" + gdp.getName() + ", " + gdp.getYear() + "] in the database");
				System.out.println(e.getMessage() + "\n");
//				e.printStackTrace();
			}
			con.commit();
		}
		ps.close();
		DriverManagerConnectionPool.getInstance().releaseConnection(con);
	}
	
	public void saveManualInsertion(CountryGDPPerCapitaData data, PrintWriter printWriter) throws SQLException
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
	
	public ArrayList<CountryGDPPerCapitaData> read(Country country) throws SQLException
	{
		ArrayList<CountryGDPPerCapitaData> data = new ArrayList<>();
		Connection con = DriverManagerConnectionPool.getInstance().getConnection();
		PreparedStatement ps = con.prepareStatement(READ);
		ps.setString(1, country.getCode());
		ResultSet rs = ps.executeQuery();
		while (rs.next())
		{
			data.add(new CountryGDPPerCapitaData(rs.getString(1), rs.getString(2), rs.getDouble(3)));
		}
		con.commit();
		rs.close();
		ps.close();
		DriverManagerConnectionPool.getInstance().releaseConnection(con);
		return data;
	}
	
	public ArrayList<CountryGDPPerCapitaData> readReal(Country country) throws SQLException
	{
		ArrayList<CountryGDPPerCapitaData> data = new ArrayList<>();
		Connection con = DriverManagerConnectionPool.getInstance().getConnection();
		PreparedStatement ps = con.prepareStatement(READ_REAL);
		ps.setString(1, country.getCode());
		ResultSet rs = ps.executeQuery();
		while (rs.next())
		{
			data.add(new CountryGDPPerCapitaData(rs.getString(1), rs.getString(2), rs.getDouble(3)));
		}
		con.commit();
		rs.close();
		ps.close();
		DriverManagerConnectionPool.getInstance().releaseConnection(con);
		return data;
	}
	
	public ArrayList<CountryGDPPerCapitaData> readByRegion(String region) throws SQLException
	{
		ArrayList<CountryGDPPerCapitaData> data = new ArrayList<>();
		Connection con = DriverManagerConnectionPool.getInstance().getConnection();
		PreparedStatement ps = con.prepareStatement(READ_BY_REGION);
		ps.setString(1, region);
		ResultSet rs = ps.executeQuery();
		while (rs.next())
		{
			data.add(new CountryGDPPerCapitaData(rs.getString(1), rs.getString(2), rs.getDouble(3)));
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
	
	public String readFirstYear() throws SQLException
	{
		String year = null;
		Connection con = DriverManagerConnectionPool.getInstance().getConnection();
		Statement stm = con.createStatement();
		ResultSet rs = stm.executeQuery(READ_FIRST_YEAR);
		if (rs.next())
		{
			year = rs.getString(1);
		}
		con.commit();
		rs.close();
		stm.close();
		DriverManagerConnectionPool.getInstance().releaseConnection(con);
		return year;
	}
	
	public ArrayList<CountryGDPPerCapitaData> readManualInsertions() throws SQLException
	{
		ArrayList<CountryGDPPerCapitaData> data = new ArrayList<>();
		Connection con = DriverManagerConnectionPool.getInstance().getConnection();
		Statement stm = con.createStatement();
		ResultSet rs = stm.executeQuery(READ_MANUAL_INSERTIONS);
		while (rs.next())
		{
			data.add(new CountryGDPPerCapitaData(rs.getString(1), rs.getString(2), rs.getDouble(3)));
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
	
	public void delete(CountryGDPPerCapitaData data, PrintWriter printWriter) throws SQLException
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
	
	private static final Logger LOGGER = Logger.getLogger("updating");
	
	private static final String CREATE = "INSERT INTO countrygdppercapita VALUES (?, ?, ?, ?, ?)";
	private static final String READ_SOURCE_BY_COUNTRY_AND_YEAR = "SELECT Source FROM countrygdppercapita WHERE Country = ? AND Year = ?";
	private static final String UPDATE = "UPDATE countrygdppercapita SET Value = ? WHERE Country = ? AND Year = ?";
	private static final String READ = "SELECT * FROM countrygdppercapita WHERE Country = ?";
	private static final String READ_REAL = "SELECT * FROM countrygdppercapita WHERE Country = ? AND Calculated = false";
	private static final String READ_BY_REGION = "SELECT cgdp.Country, cgdp.Year, cgdp.Value FROM country c, countrygdppercapita cgdp WHERE c.Code = cgdp.Country AND c.Region = ?";
	private static final String READ_VALUE_BY_COUNTRY_AND_YEAR = "SELECT Value FROM countrygdppercapita WHERE Country = ? AND Year = ?";
	private static final String READ_FIRST_YEAR = "SELECT min(Year) FROM countrygdppercapita";
	private static final String READ_MANUAL_INSERTIONS = "SELECT Country, Year, Value FROM countrygdppercapita WHERE Source = 'none'";
	private static final String DELETE = "DELETE FROM countrygdppercapita WHERE Source = ?";
	private static final String DELETE_INSERTED_DATA = "DELETE FROM countrygdppercapita WHERE Country = ? AND Year = ? AND Source = 'none'";
	
}
