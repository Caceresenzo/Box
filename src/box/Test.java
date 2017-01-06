package box;

import java.io.File;
import java.sql.SQLException;

import box.database.DatabaseManager;
import box.web.users.SimpleUser;
import box.web.users.UserManager;
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
			//DatabaseManager.DATA.executeUpdate(
			//		"INSERT INTO 'users' ('username', 'password', 'salt', 'token') VALUES ('enzo', 'pass', 'salty', 'token:caca')");
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		Logger.getLogger().info("IT WORKS!");
		
		
		/*
		 * 
		 */
		SimpleUser user = new SimpleUser("enzo", "pass");
		UserManager manager = UserManager.getManager();
		Logger.getLogger().info(":: " + user.getId() + " ::::: " + manager.checkUser("enezo", "paess"));
	}

}