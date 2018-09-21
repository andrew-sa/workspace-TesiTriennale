package manager.data.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;

import manager.connection.DriverManagerConnectionPool;
import manager.data.model.RegionPovertyData;

public class RegionPovertyDataDAO {

	public RegionPovertyDataDAO() {
		// TODO Auto-generated constructor stub
	}
	
	public void save(ArrayList<RegionPovertyData> data) throws SQLException
	{
		Connection con = DriverManagerConnectionPool.getInstance().getConnection();
		PreparedStatement ps = con.prepareStatement(CREATE);
		for (RegionPovertyData rpd: data)
		{
			ps.setString(1, rpd.getName());
			ps.setString(2, rpd.getYear());
			ps.setDouble(3, rpd.getValue());
			try
			{
				ps.executeUpdate();
			}
			catch (MySQLIntegrityConstraintViolationException e)
			{
				System.err.println("Unable to save the region poverty data [" + rpd.getName() + ", " + rpd.getYear() + "] in the database");
				System.out.println(e.getMessage() + "\n");
//				e.printStackTrace();
			}
			con.commit();
		}
		ps.close();
		DriverManagerConnectionPool.getInstance().releaseConnection(con);
	}
	
	public ArrayList<RegionPovertyData> read(String region) throws SQLException
	{
		ArrayList<RegionPovertyData> data = new ArrayList<>();
		Connection con = DriverManagerConnectionPool.getInstance().getConnection();
		PreparedStatement ps = con.prepareStatement(READ);
		ps.setString(1, region);
		ResultSet rs = ps.executeQuery();
		while (rs.next())
		{
			data.add(new RegionPovertyData(rs.getString(1), rs.getString(2), rs.getDouble(3)));
		}
		con.commit();
		rs.close();
		ps.close();
		DriverManagerConnectionPool.getInstance().releaseConnection(con);
		return data;
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

	private static final String CREATE = "INSERT INTO regionpoverty VALUES (?, ?, ?)";
	private static final String READ = "SELECT * FROM regionpoverty WHERE Region = ?";
	private static final String DELETE = "DELETE FROM regionpoverty";
	
}
