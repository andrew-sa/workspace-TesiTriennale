package old;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;

import manager.connection.DriverManagerConnectionPool;
import manager.data.model.Country;
import manager.data.model.CountryPovertyData;

public class PovertyDataDAO {

	public PovertyDataDAO() {
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
			try
			{
				ps.executeUpdate();
			}
			catch (MySQLIntegrityConstraintViolationException e)
			{
				System.err.println("Unable to save the poverty data [" + pd.getName() + ", " + pd.getYear() + "] in the database");
				System.out.println(e.getMessage() + "\n");
//				e.printStackTrace();
			}
			con.commit();
		}
		ps.close();
		DriverManagerConnectionPool.getInstance().releaseConnection(con);
	}
	
	public ArrayList<String> readCountriesOfTheSavedData() throws SQLException
	{
		ArrayList<String> countries = new ArrayList<>();
		Connection con = DriverManagerConnectionPool.getInstance().getConnection();
		Statement stm = con.createStatement();
		ResultSet rs = stm.executeQuery(READ_COUNTRIES);
		while (rs.next())
		{
			countries.add(rs.getString(1));
		}
		con.commit();
		rs.close();
		stm.close();
		DriverManagerConnectionPool.getInstance().releaseConnection(con);
		return countries;
	}
	
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
			float value = readValue(country, year);
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
	
	private float readValue(String country, String year) throws SQLException
	{
		Connection con = DriverManagerConnectionPool.getInstance().getConnection();
		PreparedStatement ps = con.prepareStatement(READ_VALUE_BY_KEY);
		ps.setString(1, country);
		ps.setString(2, year);
		ResultSet rs = ps.executeQuery();
		rs.next();
		float value = rs.getFloat(1);
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
			data.add(new CountryPovertyData(country.getCode(), rs.getString(1), rs.getFloat(2)));
		}
		con.commit();
		rs.close();
		ps.close();
		DriverManagerConnectionPool.getInstance().releaseConnection(con);
		return data;
	}
	
//	private PovertyData readValueAndNameCountry(String country, String year) throws SQLException
//	{
//		Connection con = DriverManagerConnectionPool.getInstance().getConnection();
//		PreparedStatement ps = con.prepareStatement(READ_VALUE_AND_NAME_BY_KEY);
//		ps.setString(1, country);
//		ps.setString(2, year);
//		ResultSet rs = ps.executeQuery();
//		rs.next();
//		PovertyData pd = new PovertyData(rs.getString(2), year, rs.getFloat(1));
//		con.commit();
//		rs.close();
//		ps.close();
//		DriverManagerConnectionPool.getInstance().releaseConnection(con);
//		return pd;
//	}
	
	private static final String CREATE = "INSERT INTO poverty VALUES (?, ?, ?)";
	private static final String READ_COUNTRIES = "SELECT DISTINCT Country FROM poverty";
	private static final String READ_LAST_YEAR_AVAILABLE_BY_COUNTRY = "SELECT Country, MAX(Year) FROM poverty GROUP BY Country";
	private static final String READ_VALUE_BY_KEY = "SELECT Value FROM poverty WHERE Country = ? AND Year = ?";
//	private static final String READ_VALUE_AND_NAME_BY_KEY = "SELECT Value, Name FROM country, poverty WHERE Country = ? AND Year = ? AND Code = Country";
	private static final String READ_VALUES = "SELECT Year, Value FROM poverty WHERE Country = ?";
}
