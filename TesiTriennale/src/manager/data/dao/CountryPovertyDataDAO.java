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
import manager.data.model.CountryPovertyData;
import servlets.SaveDataManualInsertion;

public class CountryPovertyDataDAO {

	public CountryPovertyDataDAO() {
		// TODO Auto-generated constructor stub
	}
	
	public void save(ArrayList<CountryPovertyData> data) throws SQLException
	{
		Connection con = DriverManagerConnectionPool.getInstance().getConnection();
		PreparedStatement ps = con.prepareStatement(CREATE);
		for (CountryPovertyData pd: data)
		{
			ps.setString(1, pd.getName());
			ps.setString(2, pd.getYear());
			ps.setDouble(3, pd.getValue());
			ps.setString(4, pd.getSource());
			ps.setBoolean(5, pd.isCalculated());
			try
			{
				LOGGER.info("SAVING: " + pd);
				ps.executeUpdate();
			}
			catch (MySQLIntegrityConstraintViolationException e)
			{
				LOGGER.warning("Unable to save " + pd + " in the database");
//				System.err.println("Unable to save the poverty data [" + pd.getName() + ", " + pd.getYear() + "] in the database");
				System.out.println(e.getMessage() + "\n");
//				e.printStackTrace();
			}
			con.commit();
		}
		ps.close();
		DriverManagerConnectionPool.getInstance().releaseConnection(con);
	}
	
	public void save(ArrayList<CountryPovertyData> data, PrintWriter printWriter) throws SQLException
	{
		Connection con = DriverManagerConnectionPool.getInstance().getConnection();
		PreparedStatement ps = con.prepareStatement(CREATE);
		for (CountryPovertyData pd: data)
		{
			ps.setString(1, pd.getName());
			ps.setString(2, pd.getYear());
			ps.setDouble(3, pd.getValue());
			ps.setString(4, pd.getSource());
			ps.setBoolean(5, pd.isCalculated());
			try
			{
				LOGGER.info("SAVING: " + pd);
				printWriter.println("SAVING: " + pd);
				printWriter.flush();
				ps.executeUpdate();
			}
			catch (MySQLIntegrityConstraintViolationException e)
			{
				LOGGER.warning("Unable to save " + pd + " in the database");
//				System.err.println("Unable to save the poverty data [" + pd.getName() + ", " + pd.getYear() + "] in the database");
				System.out.println(e.getMessage() + "\n");
//				e.printStackTrace();
			}
			con.commit();
		}
		ps.close();
		DriverManagerConnectionPool.getInstance().releaseConnection(con);
	}
	
	public void saveManualInsertion(CountryPovertyData data, PrintWriter printWriter) throws SQLException
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
	
//	public ArrayList<String> readCountriesOfTheSavedData() throws SQLException
//	{
//		ArrayList<String> countries = new ArrayList<>();
//		Connection con = DriverManagerConnectionPool.getInstance().getConnection();
//		Statement stm = con.createStatement();
//		ResultSet rs = stm.executeQuery(READ_COUNTRIES);
//		while (rs.next())
//		{
//			countries.add(rs.getString(1));
//		}
//		con.commit();
//		rs.close();
//		stm.close();
//		DriverManagerConnectionPool.getInstance().releaseConnection(con);
//		return countries;
//	}
	
	public ArrayList<CountryPovertyData> readLastValueByCountry() throws SQLException
	{
		ArrayList<CountryPovertyData> data = new ArrayList<>();
		Connection con = DriverManagerConnectionPool.getInstance().getConnection();
		Statement stm = con.createStatement();
		ResultSet rs = stm.executeQuery(READ_LAST_YEAR_AVAILABLE_BY_COUNTRY);
		while (rs.next())
		{
			String country = rs.getString(1);
			String year = rs.getString(2);
			System.out.println(country + ", " + year);
			double value = readValue(country, year);
			System.out.println("[" + country + ", " + year + ", " + value + "]");
			data.add(new CountryPovertyData(country, year, value));
			
//			data.add(readValueAndNameCountry(country, year));
		}
		con.commit();
		rs.close();
		stm.close();
		DriverManagerConnectionPool.getInstance().releaseConnection(con);
		return data;
	}
	
	private double readValue(String country, String year) throws SQLException
	{
		Connection con = DriverManagerConnectionPool.getInstance().getConnection();
		PreparedStatement ps = con.prepareStatement(READ_VALUE_BY_KEY);
		ps.setString(1, country);
		ps.setString(2, year);
		ResultSet rs = ps.executeQuery();
		rs.next();
		double value = rs.getDouble(1);
		con.commit();
		rs.close();
		ps.close();
		DriverManagerConnectionPool.getInstance().releaseConnection(con);
		return value;
	}
	
	public ArrayList<CountryPovertyData> readValuesOfTheCountry(Country country) throws SQLException
	{
		ArrayList<CountryPovertyData> data = new ArrayList<>();
		Connection con = DriverManagerConnectionPool.getInstance().getConnection();
		PreparedStatement ps = con.prepareStatement(READ_VALUES);
		ps.setString(1, country.getCode());
		ResultSet rs = ps.executeQuery();
		while (rs.next())
		{
			data.add(new CountryPovertyData(country.getCode(), rs.getString(1), rs.getDouble(2)));
		}
		con.commit();
		rs.close();
		ps.close();
		DriverManagerConnectionPool.getInstance().releaseConnection(con);
		return data;
	}
	
	public ArrayList<CountryPovertyData> readRealValuesOfTheCountry(Country country) throws SQLException
	{
		ArrayList<CountryPovertyData> data = new ArrayList<>();
		Connection con = DriverManagerConnectionPool.getInstance().getConnection();
		PreparedStatement ps = con.prepareStatement(READ_REAL_VALUES);
		ps.setString(1, country.getCode());
		ResultSet rs = ps.executeQuery();
		while (rs.next())
		{
			data.add(new CountryPovertyData(country.getCode(), rs.getString(1), rs.getDouble(2)));
		}
		con.commit();
		rs.close();
		ps.close();
		DriverManagerConnectionPool.getInstance().releaseConnection(con);
		return data;
	}
	
	public ArrayList<CountryPovertyData> readByRegionAndYear(String region, String year) throws SQLException
	{
		ArrayList<CountryPovertyData> data = new ArrayList<>();
		Connection con = DriverManagerConnectionPool.getInstance().getConnection();
		PreparedStatement ps = con.prepareStatement(READ_BY_REGION_AND_YEAR);
		ps.setString(1, region);
		ps.setString(2, year);
		ResultSet rs = ps.executeQuery();
		while (rs.next())
		{
			data.add(new CountryPovertyData(rs.getString(1), rs.getString(2), rs.getDouble(3), rs.getString(4)));
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
//			System.out.println("Value Found");
			value = rs.getDouble(1);
		}
		con.commit();
		rs.close();
		ps.close();
		DriverManagerConnectionPool.getInstance().releaseConnection(con);
		if (!Double.isNaN(value))
		{
//			System.out.println("Return value");
			return value;
		}
		else
		{
//			System.out.println("Throw Exception");
			throw new TupleNotFoundException();
		}
	}
	
	public ArrayList<String> readAvailableYearsFrom(String firstYear) throws SQLException
	{
		ArrayList<String> years = new ArrayList<>();
		Connection con = DriverManagerConnectionPool.getInstance().getConnection();
		PreparedStatement ps = con.prepareStatement(READ_YEARS);
		ps.setString(1, firstYear);
		ResultSet rs = ps.executeQuery();
		while (rs.next())
		{
			years.add(rs.getString(1));
		}
		con.commit();
		rs.close();
		ps.close();
		DriverManagerConnectionPool.getInstance().releaseConnection(con);
		return years;
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
	
	public ArrayList<CountryPovertyData> readManualInsertions() throws SQLException
	{
		ArrayList<CountryPovertyData> data = new ArrayList<>();
		Connection con = DriverManagerConnectionPool.getInstance().getConnection();
		Statement stm = con.createStatement();
		ResultSet rs = stm.executeQuery(READ_MANUAL_INSERTIONS);
		while (rs.next())
		{
			data.add(new CountryPovertyData(rs.getString(1), rs.getString(2), rs.getDouble(3)));
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
	
	public void delete(CountryPovertyData data, PrintWriter printWriter) throws SQLException
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
	
//	public static void main(String[] args) throws SQLException {
//		CountryPovertyDataDAO dao = new CountryPovertyDataDAO();
//		System.out.println(dao.readFirstYear());
//	}
	
//	private static final Logger LOGGER = Logger.getLogger(PovertyData.DATA_TYPE);
	private static final Logger LOGGER = Logger.getLogger("updating");
	
	private static final String CREATE = "INSERT INTO countrypoverty VALUES (?, ?, ?, ?, ?)";
	private static final String READ_SOURCE_BY_COUNTRY_AND_YEAR = "SELECT Source FROM countrypoverty WHERE Country = ? AND Year = ?";
	private static final String UPDATE = "UPDATE countrypoverty SET Value = ? WHERE Country = ? AND Year = ?";
//	private static final String READ_COUNTRIES = "SELECT DISTINCT Country FROM countrypoverty";
	private static final String READ_LAST_YEAR_AVAILABLE_BY_COUNTRY = "SELECT Country, MAX(Year) FROM countrypoverty WHERE Calculated = false GROUP BY Country";
	private static final String READ_VALUE_BY_KEY = "SELECT Value FROM countrypoverty WHERE Country = ? AND Year = ?";
//	private static final String READ_VALUE_AND_NAME_BY_KEY = "SELECT Value, Name FROM country, poverty WHERE Country = ? AND Year = ? AND Code = Country";
	private static final String READ_VALUES = "SELECT Year, Value FROM countrypoverty WHERE Country = ?";
	private static final String READ_REAL_VALUES = "SELECT Year, Value FROM countrypoverty WHERE Country = ? AND Calculated = false";
	private static final String READ_BY_REGION_AND_YEAR = "SELECT p.Country, p.Year, p.Value, p.Source FROM country c, countrypoverty p WHERE c.Region = ? AND p.Year = ? AND c.Code = p.Country";
	private static final String READ_VALUE_BY_COUNTRY_AND_YEAR = "SELECT Value FROM countrypoverty WHERE Country = ? AND Year = ?";
	private static final String READ_YEARS = "SELECT DISTINCT Year FROM countrypoverty WHERE Year >= ?";
	private static final String READ_FIRST_YEAR = "SELECT min(Year) FROM countrypoverty";
	private static final String READ_MANUAL_INSERTIONS = "SELECT Country, Year, Value FROM countrypoverty WHERE Source = 'none'";
	private static final String DELETE = "DELETE FROM countrypoverty WHERE Source = ?";
	private static final String DELETE_INSERTED_DATA = "DELETE FROM countrypoverty WHERE Country = ? AND Year = ? AND Source = 'none'";
}
