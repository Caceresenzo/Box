package box.database;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.SQLException;

import box.database.sqlite.SQLite;
import net.sociuris.logger.Logger;

public class DatabaseManager {
	
	private static DatabaseManager manager = new DatabaseManager();
	
	private final Logger logger = Logger.getLogger();
	
	private DatabaseManager() {
		
	}
	
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
			
			
			try {
				SecureRandom.getInstance("SHA1PRNG");
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			if (Database.web.checkConnection()) {
				Database.web.querySQL(""
						+ "CREATE TABLE web_session("
						+ "session_id     CHAR(50)     PRIMARY KEY    NOT NULL,"
						+ "session_ip     TEXT         NOT NULL,"
						+ "session_date   DATETIME(6)  NOT NULL,"
						+ ");");
			}
		} catch (SQLException | ClassNotFoundException exception) {
			exception.printStackTrace();
		}
	}
	
}