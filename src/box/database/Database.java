package box.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import box.Bootstrap;
import net.sociuris.logger.Logger;

public class Database {

	private final Logger logger = Logger.getLogger();
	private Connection connection;
	private String name;

	/**
	 * Create a new database with the given name
	 * 
	 * @param name
	 */
	public Database(String name) {
		this.name = name;
	}

	/**
	 * Open a connection with the database
	 * 
	 * @return the connection
	 * @throws SQLException
	 */
	public Connection openConnection() throws SQLException {
		if (!isConnected())
			connection = DriverManager.getConnection("jdbc:sqlite:" + Bootstrap.getWorkingDirectory() + "database/" + name + ".db");
		logger.debug("Opened database \"%s\" successfully", name);
		return connection;
	}

	/**
	 * Closes the connection
	 * 
	 * @return {@code true} if the connection is closed, {@code false} if
	 *         already closed
	 * @throws SQLException
	 */
	public boolean closeConnection() throws SQLException {
		if (connection == null)
			return false;
		else {
			logger.debug("Close database \"%s\" connection", name);
			connection.close();
			return true;
		}
	}

	/**
	 * Executes a SQL query (if this connection is closed, it will be opened)
	 * 
	 * @param query
	 * @return the result of the query
	 * @throws SQLException
	 */
	public ResultSet executeQuery(String query) throws SQLException {
		if (!isConnected())
			openConnection();
		Statement statement = connection.createStatement();
		logger.debug("Execute query \"%s\" to datase %s", query, name);
		return statement.executeQuery(query);
	}

	public void executeQuery(String query, DatabaseQueryResult queryResult) throws SQLException {
		if (!isConnected())
			openConnection();
		Statement statement = connection.createStatement();
		logger.debug("Execute query \"%s\" to datase %s", query, name);
		ResultSet resultSet = statement.executeQuery(query);
		while (resultSet.next())
			queryResult.nextLine(resultSet);
	}

	/**
	 * Executes a SQL update (if this connection is closed, it will be opened)
	 * 
	 * @param query
	 * @return Returns the line number updated by the statement or 0 if it
	 *         returns nothing (see
	 *         {@link java.sql.Statement#executeUpdate(String)})
	 * @throws SQLException
	 */
	public int executeUpdate(String query) throws SQLException {
		if (!isConnected())
			openConnection();
		Statement statement = connection.createStatement();
		statement.closeOnCompletion();
		logger.debug("Execute update \"%s\" to database %s", query, name);
		return statement.executeUpdate(query);
	}

	/**
	 * Checks if the connection is open with the database
	 * 
	 * @return {@code true} if the connection is open, {@code false} otherwise
	 * @throws SQLException
	 */
	public boolean isConnected() throws SQLException {
		return (connection != null && !connection.isClosed());
	}

	/**
	 * Gets the connection of database
	 * 
	 * @return the connection or {@code null} if doesn't exist
	 */
	public Connection getConnection() {
		return connection;
	}

	/**
	 * Gets the name of database
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}

}