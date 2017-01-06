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
			Class.forName("org.sqlite.JDBC");

			if (DatabaseManager.WEB != null && WEB.isConnected())
				WEB.closeConnection();

			if (DATA != null && DATA.isConnected())
				DATA.closeConnection();

			WEB = new Database("web");
			DATA = new Database("data");

			WEB.openConnection();
			DATA.openConnection();
		} catch (SQLException | ClassNotFoundException exception) {
			CrashReport.makeCrashReport("An error occurred while creating default databases!", exception);
		}
	}

	public void initDatabase() {
		try {
			if (WEB.isConnected()) {
				StringBuilder builder = new StringBuilder();
				builder
					.append("DROP TABLE IF EXISTS `web_sessions`;")
					.append("CREATE TABLE IF NOT EXISTS `web_sessions` (")
					.append("  `session_id`     varchar(255)  NOT NULL,")
					.append("  `session_ip`     varchar(16)   NOT NULL,")
					.append("  `session_date`   datetime      NOT NULL DEFAULT CURRENT_TIMESTAMP,")
					.append("  `session_token`  varchar(100)  NOT NULL,")
					.append("  PRIMARY KEY (`session_id`)")
					.append(");");
				int response = WEB.executeUpdate(builder.toString());
				logger.debug("Database response: %s", response);
			}
			if (DATA.isConnected()) {
				StringBuilder builder = new StringBuilder();
				builder
					.append("CREATE TABLE IF NOT EXISTS `users` (")
					.append("  `id`        INTEGER PRIMARY KEY AUTOINCREMENT,")
					.append("  `username`  varchar(16)   NOT NULL,")
					.append("  `password`  text          NOT NULL,")
					.append("  `salt`      text          NOT NULL,")
					.append("  `token`     varchar(100)  NOT NULL")
					.append(");");
				int response = DATA.executeUpdate(builder.toString());
				logger.debug("Database response: %s", response);
			}
		} catch (SQLException exception) {
			CrashReport.makeCrashReport("An error occurred while initialize databases!", exception);
		}
	}

}