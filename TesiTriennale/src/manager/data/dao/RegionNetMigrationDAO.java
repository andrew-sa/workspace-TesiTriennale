package manager.data.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;

import manager.connection.DriverManagerConnectionPool;
import manager.data.model.RegionNetMigrationData;

public class RegionNetMigrationDAO {
	
	public void save(ArrayList<RegionNetMigrationData> data) throws SQLException
	{
		Connection con = DriverManagerConnectionPool.getInstance().getConnection();
		PreparedStatement ps = con.prepareStatement(CREATE);
		for (RegionNetMigrationData rnmd: data)
		{
			ps.setString(1, rnmd.getName());
			ps.setString(2, rnmd.getYear());
			ps.setDouble(3, rnmd.getValue());
			try
			{
				ps.executeUpdate();
			}
			catch (MySQLIntegrityConstraintViolationException e)
			{
				System.err.println("Unable to save the region net migration data [" + rnmd.getName() + ", " + rnmd.getYear() + "] in the database");
				System.out.println(e.getMessage() + "\n");
//				e.printStackTrace();
			}
			con.commit();
		}
		ps.close();
		DriverManagerConnectionPool.getInstance().releaseConnection(con);
	}
	
	public ArrayList<RegionNetMigrationData> read(String region) throws SQLException
	{
		ArrayList<RegionNetMigrationData> data = new ArrayList<>();
		Connection con = DriverManagerConnectionPool.getInstance().getConnection();
		PreparedStatement ps = con.prepareStatement(READ);
		ps.setString(1, region);
		ResultSet rs = ps.executeQuery();
		while (rs.next())
		{
			data.add(new RegionNetMigrationData(rs.getString(1), rs.getString(2), rs.getDouble(3)));
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
	
	private static final String CREATE = "INSERT INTO regionnetmigration VALUES (?, ?, ?)";
	private static final String READ = "SELECT * FROM regionnetmigration WHERE Region = ?";
	private static final String DELETE = "DELETE FROM regionnetmigration";

}
