package box.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import box.database.sqlite.SQLite;
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
			if (Database.web != null && Database.web.checkConnection()) {
				Database.web.closeConnection();
			}
			if (Database.data != null && Database.data.checkConnection()) {
				Database.data.closeConnection();
			}
			
			Database.web = new SQLite("web.db");
			Database.data = new SQLite("data.db");
			
			Database.web.openConnection();
			Database.data.openConnection();
		} catch (ClassNotFoundException | SQLException exception) {
			logger.error("An error occurred while creating default databases!");
			logger.printStackTrace(exception);
		}
	}
	
	public void initDatabase() {
		try {
			if (Database.web.checkConnection()) {
				String query = ""
						+ "DROP TABLE IF EXISTS `web_session`;"
						+ "CREATE TABLE IF NOT EXISTS `web_session` ("
						+ "  `session_id` varchar(100) NOT NULL,"
						+ "  `session_ip` varchar(16) NOT NULL,"
						+ "  `session_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,"
						+ "  PRIMARY KEY (`session_id`)"
						+ ");";
				Statement statement = Database.web.getConnection().createStatement();
				Integer response = statement.executeUpdate(query);
				statement.close();
				System.out.println(": " + response);
			}
		} catch (SQLException exception) {
			exception.printStackTrace();
		}
	}
	
}