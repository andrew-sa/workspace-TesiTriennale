package manager.data.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import manager.connection.DriverManagerConnectionPool;
import manager.data.model.Source;

public class SourceDAO {

	public SourceDAO() {
		// TODO Auto-generated constructor stub
	}
	
	public ArrayList<Source> read() throws SQLException
	{
		ArrayList<Source> sources = new ArrayList<>();
		Connection con = DriverManagerConnectionPool.getInstance().getConnection();
		Statement stm = con.createStatement();
		ResultSet rs = stm.executeQuery(READ);
		while (rs.next())
		{
			sources.add(new Source(rs.getString(1), rs.getString(2), rs.getDate(3), rs.getInt(4)));
		}
		con.commit();
		rs.close();
		DriverManagerConnectionPool.getInstance().releaseConnection(con);
		return sources;
	}
	
	public ArrayList<String> readNames() throws SQLException
	{
		ArrayList<String> names = new ArrayList<>();
		Connection con = DriverManagerConnectionPool.getInstance().getConnection();
		Statement stm = con.createStatement();
		ResultSet rs = stm.executeQuery(READ_NAMES);
		while (rs.next())
		{
			names.add(rs.getString(1));
		}
		con.commit();
		rs.close();
		DriverManagerConnectionPool.getInstance().releaseConnection(con);
		return names;
	}
	
	public ArrayList<String> readDataTypes() throws SQLException
	{
		ArrayList<String> dataTypes = new ArrayList<>();
		Connection con = DriverManagerConnectionPool.getInstance().getConnection();
		Statement stm = con.createStatement();
		ResultSet rs = stm.executeQuery(READ_DATA_TYPES);
		while (rs.next())
		{
			dataTypes.add(rs.getString(1));
		}
		con.commit();
		rs.close();
		DriverManagerConnectionPool.getInstance().releaseConnection(con);
		return dataTypes;
	}
	
	public void update(Source source) throws SQLException
	{
		Connection con = DriverManagerConnectionPool.getInstance().getConnection();
		PreparedStatement ps = con.prepareStatement(UPDATE);
		ps.setDate(1, new Date((new GregorianCalendar()).getTimeInMillis()));
		ps.setString(2, source.getName());
		ps.setString(3, source.getDataType());
		ps.executeUpdate();
		con.commit();
		ps.close();
		DriverManagerConnectionPool.getInstance().releaseConnection(con);
	}
	
	public void update(String sourceName, String dataType) throws SQLException
	{
		Connection con = DriverManagerConnectionPool.getInstance().getConnection();
		PreparedStatement ps = con.prepareStatement(UPDATE);
		ps.setDate(1, new Date((new GregorianCalendar()).getTimeInMillis()));
		ps.setString(2, sourceName);
		ps.setString(3, dataType);
		ps.executeUpdate();
		con.commit();
		ps.close();
		DriverManagerConnectionPool.getInstance().releaseConnection(con);
	}
	
//	public static void main(String[] args) {
//		System.out.println(new Date((new GregorianCalendar()).getTimeInMillis()));
//	}
	
	private static final String READ = "SELECT * FROM source WHERE Name != 'none'";
	private static final String READ_NAMES = "SELECT DISTINCT Name FROM source WHERE Name != 'none'";
	private static final String READ_DATA_TYPES = "SELECT DISTINCT DataType FROM source WHERE Name != 'none'";
	private static final String UPDATE = "UPDATE source SET LastUpdate = ? WHERE Name = ? AND DataType = ?";

}
