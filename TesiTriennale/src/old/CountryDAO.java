package old;

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
	
	public ArrayList<Country> readAll() throws SQLException
	{
		ArrayList<Country> countries = new ArrayList<>();
		Connection con = DriverManagerConnectionPool.getInstance().getConnection();
		Statement stm = con.createStatement();
		ResultSet rs = stm.executeQuery(READ_ALL);
		while (rs.next())
		{
			Country country = new Country(rs.getString(1), rs.getString(2));
			countries.add(country);
		}
		con.commit();
		rs.close();
		stm.close();
		DriverManagerConnectionPool.getInstance().releaseConnection(con);
		return countries;
	}
	
	public static void main(String[] args) {
		LOGGER.severe("we");
	}
	
	private static final Logger LOGGER = Logger.getLogger("updating");

	private static final String CREATE = "INSERT INTO country VALUES (?, ?)";
	private static final String READ = "SELECT * FROM country WHERE Code = ?";
	private static final String READ_ALL = "SELECT DISTINCT c.code, c.name FROM country c, poverty p WHERE c.Code = p.Country";

}
