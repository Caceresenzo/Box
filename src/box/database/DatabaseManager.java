package box.database;

import java.sql.SQLException;

import net.sociuris.crash.CrashReport;
import net.sociuris.logger.Logger;

public class DatabaseManager {

	private static final DatabaseManager DATABASE_MANAGER = new DatabaseManager();

	public static DatabaseManager getManager() {
		return DATABASE_MANAGER;
	}
	
	public static Database WEB;
	public static Database DATA;

	private final Logger logger = Logger.getLogger();

	private DatabaseManager() {
	}

	public void loadDatabase() {
		try {
			if (DatabaseManager.WEB != null && WEB.isConnected())
				WEB.closeConnection();

			if (DATA != null && DATA.isConnected())
				DATA.closeConnection();

			WEB = new Database("web");
			DATA = new Database("data");

			WEB.openConnection();
			DATA.openConnection();
		} catch (SQLException e) {
			CrashReport.makeCrashReport("An error occurred while creating default databases!", e);
		}
	}

	public void initDatabase() {
		try {
			if (WEB.isConnected()) {
				StringBuilder builder = new StringBuilder();
				//builder.append("DROP TABLE IF EXISTS 'web_sessions';");
				builder.append("CREATE TABLE IF NOT EXISTS 'web_sessions' (");
				builder.append("'session_id' varchar(255) NOT NULL,");
				builder.append("'session_ip' varchar(16) NOT NULL,");
				builder.append("'session_date' datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,");
				builder.append("'session_token' varchar(100) NOT NULL,");
				builder.append("PRIMARY KEY ('session_id')");
				builder.append(");");
				/*String query = "" + "DROP TABLE IF EXISTS `web_session`;" + "CREATE TABLE IF NOT EXISTS `web_session` ("
						+ "  `session_id` varchar(100) NOT NULL," + "  `session_ip` varchar(16) NOT NULL,"
						+ "  `session_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,"
						+ "  `session_token` varchar(100) NOT NULL," + "  PRIMARY KEY (`session_id`)" + ");";*/

				int response = WEB.executeUpdate(builder.toString());
				logger.debug("Database response: %s", response);
			}
		} catch (SQLException e) {
			CrashReport.makeCrashReport("An error occurred while initialize databases!", e);
		}
	}

}