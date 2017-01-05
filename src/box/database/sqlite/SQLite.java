package box.database.sqlite;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import box.database.Database;
import box.utils.FileUtils;

/**
 * Connects to and uses a SQLite database
 *
 * @author Enzo CACERES
 */
public class SQLite extends Database {
	private final String dbLocation;
	
	/**
	 * Creates a new SQLite instance
	 *
	 * @param dbLocation Location of the Database (Must end in .db)
	 */
	public SQLite(String dbLocation) {
		this.dbLocation = dbLocation;
		this.dbName = dbLocation.replace(FileUtils.getExtension(dbLocation), "");
	}
	
	@Override
	public Connection openConnection() throws SQLException, ClassNotFoundException {
		if (checkConnection()) {
			return connection;
		}
		
		File dataFolder = new File("database/");
		if (!dataFolder.exists()) {
			dataFolder.mkdirs();
		}
		
		File file = new File(dataFolder, dbLocation);
		if (!(file.exists())) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				System.out.println("Unable to create database!");
			}
		}
		Class.forName("org.sqlite.JDBC");
		connection = DriverManager.getConnection("jdbc:sqlite:" + dataFolder + "/" + dbLocation);
		return connection;
	}
}