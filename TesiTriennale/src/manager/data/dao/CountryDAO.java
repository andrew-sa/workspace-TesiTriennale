package manager.data.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;

import exceptions.TupleNotFoundException;
import manager.connection.DriverManagerConnectionPool;
import manager.data.extraction.WorldBankDataWrapper;
import manager.data.model.Country;
import manager.data.model.GDPPerCapitaData;
import manager.data.model.NetMigrationData;
import manager.data.model.PopulationData;
import manager.data.model.PovertyData;

public class CountryDAO {

	public CountryDAO() {
		// TODO Auto-generated constructor stub
	}
	
	public void save(ArrayList<Country> countries) throws SQLException
	{
		Connection con = DriverManagerConnectionPool.getInstance().getConnection();
		PreparedStatement ps = con.prepareStatement(CREATE);
		for (Country c: countries)
		{
			ps.setString(1, c.getCode());
			ps.setString(2, c.getName());
			ps.setString(3, c.getRegion());
			try
			{
				ps.executeUpdate();
			}
			catch (MySQLIntegrityConstraintViolationException e)
			{
				System.err.println("Unable to save the country " + c.getName() + " in the database");
				e.printStackTrace();
			}
			con.commit();
		}
		ps.close();
		DriverManagerConnectionPool.getInstance().releaseConnection(con);
	}
	
	public Country read(String code) throws SQLException, TupleNotFoundException
	{
		Connection con = DriverManagerConnectionPool.getInstance().getConnection();
		PreparedStatement ps = con.prepareStatement(READ);
		ps.setString(1, code);
		ResultSet rs = ps.executeQuery();
		if (rs.next())
		{
			Country country = new Country(code, rs.getString(2));
			con.commit();
			rs.close();
			ps.close();
			DriverManagerConnectionPool.getInstance().releaseConnection(con);
			return country;
		}
		else
		{
			con.commit();
			rs.close();
			ps.close();
			DriverManagerConnectionPool.getInstance().releaseConnection(con);
			throw new TupleNotFoundException();
		}
	}
	
	public ArrayList<Country> readAllAvailableForAnalysisChart() throws SQLException
	{
		ArrayList<Country> countries = new ArrayList<>();
		Connection con = DriverManagerConnectionPool.getInstance().getConnection();
		Statement stm = con.createStatement();
		ResultSet rs = stm.executeQuery(READ_ALL_FOR_ANALYSIS_CHART);
		while (rs.next())
		{
			Country country = new Country(rs.getString(1), rs.getString(2), rs.getString(3));
			countries.add(country);
		}
		con.commit();
		rs.close();
		stm.close();
		DriverManagerConnectionPool.getInstance().releaseConnection(con);
		return countries;
	}
	
	public ArrayList<Country> readAllAvailableForAnimatedChart(String firstYear) throws SQLException
	{
		ArrayList<Country> countries = new ArrayList<>();
		Connection con = DriverManagerConnectionPool.getInstance().getConnection();
		PreparedStatement ps = con.prepareStatement(READ_ALL_FOR_ANIMATED_CHART);
		ps.setString(1, firstYear);
		ResultSet rs = ps.executeQuery();
		while (rs.next())
		{
			Country country = new Country(rs.getString(1), rs.getString(2), rs.getString(3));
			countries.add(country);
		}
		con.commit();
		rs.close();
		ps.close();
		DriverManagerConnectionPool.getInstance().releaseConnection(con);
		return countries;
	}
	
	public ArrayList<String> readRegions() throws SQLException
	{
		ArrayList<String> regions = new ArrayList<>();
		Connection con = DriverManagerConnectionPool.getInstance().getConnection();
		Statement stm = con.createStatement();
		ResultSet rs = stm.executeQuery(READ_REGIONS);
		while (rs.next())
		{
			regions.add(rs.getString(1));
		}
		con.commit();
		rs.close();
		stm.close();
		DriverManagerConnectionPool.getInstance().releaseConnection(con);
		return regions;
	}
	
	public ArrayList<Country> readCountryForWorldBankOfADataType(String dataType) throws SQLException
	{
		ArrayList<Country> countries = new ArrayList<>();
		if (null != dataType)
		{
			Connection con = DriverManagerConnectionPool.getInstance().getConnection();
//			Statement stm = con.createStatement();
			PreparedStatement ps = null;
			ResultSet rs = null;
			switch (dataType)
			{
				case PopulationData.DATA_TYPE:
//					rs = stm.executeQuery(READ_COUNTRY_FOR_WORLD_BANK_POPULATION_DATA);
					ps = con.prepareStatement(READ_COUNTRY_FOR_WORLD_BANK_POPULATION_DATA);
					ps.setString(1, WorldBankDataWrapper.SOURCE);
					rs = ps.executeQuery();
					break;
				case PovertyData.DATA_TYPE:
//					rs = stm.executeQuery(READ_COUNTRY_FOR_WORLD_BANK_POVERTY_DATA);
					ps = con.prepareStatement(READ_COUNTRY_FOR_WORLD_BANK_POVERTY_DATA);
					ps.setString(1, WorldBankDataWrapper.SOURCE);
					rs = ps.executeQuery();
					break;
				case NetMigrationData.DATA_TYPE:
//					rs = stm.executeQuery(READ_COUNTRY_FOR_WORLD_BANK_NET_MIGRATION_DATA);
					ps = con.prepareStatement(READ_COUNTRY_FOR_WORLD_BANK_NET_MIGRATION_DATA);
					ps.setString(1, WorldBankDataWrapper.SOURCE);
					rs = ps.executeQuery();
					break;
				case GDPPerCapitaData.DATA_TYPE:
//					rs = stm.executeQuery(READ_COUNTRY_FOR_WORLD_BANK_GDP_PER_CAPITA_DATA);
					ps = con.prepareStatement(READ_COUNTRY_FOR_WORLD_BANK_GDP_PER_CAPITA_DATA);
					ps.setString(1, WorldBankDataWrapper.SOURCE);
					rs = ps.executeQuery();
					break;
				default:
					break;
			}
			if (null != rs)
			{
				while (rs.next())
				{
					countries.add(new Country(rs.getString(1), rs.getString(2)));
				}
				con.commit();
				rs.close();
			}
			else
			{
				System.err.println("Invalid Data Type");
				con.commit();
			}
			if (null != ps)
			{
				ps.close();
			}
			DriverManagerConnectionPool.getInstance().releaseConnection(con);
		}
		else
		{
			System.err.println("Null Data Type");
		}
		System.out.println(countries.size());
		return countries;
	}
	
	public void delete() throws SQLException
	{
		Connection con = DriverManagerConnectionPool.getInstance().getConnection();
		Statement stm = con.createStatement();
		stm.execute(DELETE);
		con.commit();
		stm.close();
		DriverManagerConnectionPool.getInstance().releaseConnection(con);
	}
	
//	public ArrayList<Country> readCountryForWorldBankGDPPerCapitaDATA() throws SQLException
//	{
//		ArrayList<Country> countries = new ArrayList<>();
//		Connection con = DriverManagerConnectionPool.getInstance().getConnection();
//		Statement stm = con.createStatement();
//		ResultSet rs = stm.executeQuery(READ_COUNTRY_FOR_WORLD_BANK_GDP_PER_CAPITA_DATA);
//		while (rs.next())
//		{
//			countries.add(new Country(rs.getString(1), rs.getString(2)));
//		}
////		System.out.println(countries.size());
//		con.commit();
//		rs.close();
//		stm.close();
//		DriverManagerConnectionPool.getInstance().releaseConnection(con);
//		return countries;
//	}
	
//	public static void main(String[] args) throws SQLException {
//		System.out.println((new CountryDAO()).readAllAvailableForAnalysisChart());
//		System.out.println((new CountryDAO()).readAllAvailableForAnalysisChart().size());
//	}

	private static final String CREATE = "INSERT INTO country VALUES (?, ?, ?)";
	private static final String READ = "SELECT * FROM country WHERE Code = ?";
//	private static final String READ_ALL = "SELECT DISTINCT c.Ccode, c.Name, c.Region FROM country c, countrypoverty p WHERE c.Code = p.Country";
	private static final String READ_ALL_FOR_ANALYSIS_CHART = "SELECT c.Code, c.Name, c.Region FROM country c WHERE c.Code IN (SELECT DISTINCT Country FROM countrypopulation) OR c.Code IN (SELECT DISTINCT Country FROM countrynetmigration) OR c.Code IN (SELECT DISTINCT Country FROM countrygdppercapita)";
	private static final String READ_ALL_FOR_ANIMATED_CHART = "SELECT DISTINCT c.Code, c.Name, c.Region FROM country c, countrypoverty p WHERE c.Code = p.Country AND p.Year >= ?";
	private static final String READ_REGIONS = "SELECT DISTINCT c.Region FROM country c, countrypoverty p WHERE c.Code = p.Country";
	private static final String READ_COUNTRY_FOR_WORLD_BANK_POPULATION_DATA = "SELECT * FROM country WHERE Code NOT IN (SELECT DISTINCT Country FROM countrypopulation WHERE Source != ?)";
	private static final String READ_COUNTRY_FOR_WORLD_BANK_POVERTY_DATA = "SELECT * FROM country WHERE Code NOT IN (SELECT DISTINCT Country FROM countrypoverty WHERE Source != ?)";
	private static final String READ_COUNTRY_FOR_WORLD_BANK_NET_MIGRATION_DATA = "SELECT * FROM country WHERE Code NOT IN (SELECT DISTINCT Country FROM countrynetmigration WHERE Source != ?)";
	private static final String READ_COUNTRY_FOR_WORLD_BANK_GDP_PER_CAPITA_DATA = "SELECT * FROM country WHERE Code NOT IN (SELECT DISTINCT Country FROM countrygdppercapita WHERE Source != ?)";
	private static final String DELETE = "DELETE FROM country";
	
}
