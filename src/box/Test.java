package box;

import java.io.File;
import java.sql.SQLException;

import box.database.DatabaseManager;
import net.sociuris.crash.CrashReport;
import net.sociuris.logger.Logger;

public class Test {

	private static DatabaseManager dbmanager = DatabaseManager.getManager();
	private static File workingDirectory = new File(System.getProperty("user.dir", "."));

	public static void main(String[] args) {
		Logger.getLogger().setDebug(true);

		CrashReport.setDirectory(new File(workingDirectory, "crash-reports"));
		Logger.getLogger().setDirectory(new File(workingDirectory, "logs"));

		dbmanager.loadDatabase();
		dbmanager.initDatabase();
		try {
			DatabaseManager.WEB.executeUpdate(
					"INSERT INTO 'web_sessions' ('session_id', 'session_ip', 'session_token') VALUES ('key', '90.19.73.219', 'gfe54546e4')");
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		try {
			DatabaseManager.WEB.closeConnection();
			DatabaseManager.DATA.closeConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		Logger.getLogger().info("IT WORKS!");
	}

}