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

import manager.connection.DriverManagerConnectionPool;
import manager.data.model.Country;
import manager.data.model.CountryNetMigrationData;

public class CountryNetMigrationDataDAO {

	public CountryNetMigrationDataDAO() {
		// TODO Auto-generated constructor stub
	}

	public void save(ArrayList<CountryNetMigrationData> data) throws SQLException
	{
		Connection con = DriverManagerConnectionPool.getInstance().getConnection();
		PreparedStatement ps = con.prepareStatement(CREATE);
		for (CountryNetMigrationData nmd: data)
		{
			ps.setString(1, nmd.getName());
			ps.setString(2, nmd.getYear());
			ps.setDouble(3, nmd.getValue());
			ps.setString(4, nmd.getSource());
			try
			{
				LOGGER.info("SAVING: " + nmd);
				ps.executeUpdate();
			}
			catch (MySQLIntegrityConstraintViolationException e)
			{
				LOGGER.warning("Unable to save " + nmd + " in the database");
//				System.err.println("Unable to save the net migration data [" + nmd.getName() + ", " + nmd.getYear() + "] in the database");
				System.out.println(e.getMessage() + "\n");
//				e.printStackTrace();
			}
			con.commit();
		}
		ps.close();
		DriverManagerConnectionPool.getInstance().releaseConnection(con);
	}
	
	public void save(ArrayList<CountryNetMigrationData> data, PrintWriter printWriter) throws SQLException
	{
		Connection con = DriverManagerConnectionPool.getInstance().getConnection();
		PreparedStatement ps = con.prepareStatement(CREATE);
		for (CountryNetMigrationData nmd: data)
		{
			ps.setString(1, nmd.getName());
			ps.setString(2, nmd.getYear());
			ps.setDouble(3, nmd.getValue());
			ps.setString(4, nmd.getSource());
			try
			{
				LOGGER.info("SAVING: " + nmd);
				printWriter.println("SAVING: " + nmd);
				printWriter.flush();
				ps.executeUpdate();
			}
			catch (MySQLIntegrityConstraintViolationException e)
			{
				LOGGER.warning("Unable to save " + nmd + " in the database");
//				System.err.println("Unable to save the net migration data [" + nmd.getName() + ", " + nmd.getYear() + "] in the database");
				System.out.println(e.getMessage() + "\n");
//				e.printStackTrace();
			}
			con.commit();
		}
		ps.close();
		DriverManagerConnectionPool.getInstance().releaseConnection(con);
	}
	
	public ArrayList<CountryNetMigrationData> read(Country country) throws SQLException
	{
		ArrayList<CountryNetMigrationData> data = new ArrayList<>();
		Connection con = DriverManagerConnectionPool.getInstance().getConnection();
		PreparedStatement ps = con.prepareStatement(READ);
		ps.setString(1, country.getCode());
		ResultSet rs = ps.executeQuery();
		while (rs.next())
		{
			data.add(new CountryNetMigrationData(rs.getString(1), rs.getString(2), rs.getDouble(3)));
		}
		con.commit();
		rs.close();
		ps.close();
		DriverManagerConnectionPool.getInstance().releaseConnection(con);
		return data;
	}
	
	public ArrayList<CountryNetMigrationData> readByRegion(String region) throws SQLException
	{
		ArrayList<CountryNetMigrationData> data = new ArrayList<>();
		Connection con = DriverManagerConnectionPool.getInstance().getConnection();
		PreparedStatement ps = con.prepareStatement(READ_BY_REGION);
		ps.setString(1, region);
		ResultSet rs = ps.executeQuery();
		while (rs.next())
		{
			data.add(new CountryNetMigrationData(rs.getString(1), rs.getString(2), rs.getDouble(3)));
		}
		con.commit();
		rs.close();
		ps.close();
		DriverManagerConnectionPool.getInstance().releaseConnection(con);
		return data;
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

	private static final Logger LOGGER = Logger.getLogger("updating");
	
	private static final String CREATE = "INSERT INTO countrynetmigration VALUES (?, ?, ?, ?)";
	private static final String READ = "SELECT * FROM countrynetmigration WHERE Country = ?";
	private static final String READ_BY_REGION = "SELECT cnt.Country, cnt.Year, cnt.Value FROM country c, countrynetmigration cnt WHERE c.Code = cnt.Country AND c.Region = ?";
	private static final String READ_FIRST_YEAR = "SELECT min(Year) FROM countrynetmigration";
	private static final String DELETE = "DELETE FROM countrynetmigration WHERE Source = ?";
	
}
