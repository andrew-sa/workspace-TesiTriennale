package manager.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 * Classe che gestisce la connessione con il DB
 */

public class DriverManagerConnectionPool {
	
	private DriverManagerConnectionPool()
	{
		freeDbConnections = new LinkedList<Connection>();
		final String driver = "com.mysql.jdbc.Driver";
		try
		{
			Class.forName(driver);
		}
		catch (ClassNotFoundException e)
		{
			System.out.println("DB driver not found");
			e.printStackTrace();
		}
	}
	
	public static synchronized DriverManagerConnectionPool getInstance()
	{
        if (instance == null)
        {
        	instance = new DriverManagerConnectionPool();
        }
        return instance;
    }
	
	private Connection createDBConnection() throws SQLException
	{
		Connection newConnection = null;
		newConnection = DriverManager.getConnection(url, user, password);
		newConnection.setAutoCommit(false);
		return newConnection;
	}
	
	public synchronized Connection getConnection() throws SQLException
	{
		Connection connection;
		if (!freeDbConnections.isEmpty())
		{
			connection = (Connection) freeDbConnections.get(0);
			DriverManagerConnectionPool.freeDbConnections.remove(0);
			try
			{
				if (connection.isClosed())
				{
					connection = getConnection();
				}
			}
			catch (SQLException e)
			{
				connection = getConnection();
			}
		}
		else
		{
			connection = createDBConnection();
		}
		return connection;
	}
	
	public synchronized void releaseConnection(Connection connection)
	{
		DriverManagerConnectionPool.freeDbConnections.add(connection);
	}

	private static DriverManagerConnectionPool instance = null;
	private static List<Connection> freeDbConnections;
	private static final String url = "jdbc:mysql://localhost:3306/tesitriennale";
	private static final String user = "root";
	private static final String password = "";

}
