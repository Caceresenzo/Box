package box.database;

import java.sql.SQLException;
import java.sql.Statement;

import net.sociuris.logger.Logger;

public class DatabaseManager {
	
	private static DatabaseManager manager = new DatabaseManager();
	
	private final Logger logger = Logger.getLogger();
	
	private DatabaseManager() { }
	
	public static DatabaseManager getManager() {
		return manager;
	}
	
	public void setupDatabase() {
		try {
			if (Databases.web != null && Databases.web.checkConnection()) {
				Databases.web.closeConnection();
			}
			if (Databases.data != null && Databases.data.checkConnection()) {
				Databases.data.closeConnection();
			}
			
			Databases.web = new Database("web.db");
			Databases.data = new Database("data.db");
			
			Databases.web.openConnection();
			Databases.data.openConnection();
		} catch (ClassNotFoundException | SQLException exception) {
			logger.error("An error occurred while creating default databases!");
			logger.printStackTrace(exception);
		}
	}
	
	public void initDatabase() {
		try {
			if (Databases.web.checkConnection()) {
				String query = ""
						+ "DROP TABLE IF EXISTS `web_session`;"
						+ "CREATE TABLE IF NOT EXISTS `web_session` ("
						+ "  `session_id` varchar(100) NOT NULL,"
						+ "  `session_ip` varchar(16) NOT NULL,"
						+ "  `session_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,"
						+ "  `session_token` varchar(100) NOT NULL,"
						+ "  PRIMARY KEY (`session_id`)"
						+ ");";
				Statement statement = Databases.web.getConnection().createStatement();
				Integer response = statement.executeUpdate(query);
				statement.close();
				System.out.println(": " + response);
			}
		} catch (SQLException exception) {
			exception.printStackTrace();
		}
	}
	
}