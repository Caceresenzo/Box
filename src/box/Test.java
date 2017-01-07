package box;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import box.database.DatabaseManager;
import box.minecraft.MinecraftServer;
import box.minecraft.ServerManager;
import box.minecraft.exception.ServerStopException;
import box.web.users.User;
import box.web.users.UserManager;
import net.sociuris.configuration.ConfigurationFile;
import net.sociuris.crash.CrashReport;
import net.sociuris.logger.Logger;

public class Test {
	
	private static final Logger logger = Logger.getLogger();

	private static DatabaseManager dbmanager = DatabaseManager.getManager();
	private static UserManager usermanager = UserManager.getManager();
	private static File workingDirectory = new File(System.getProperty("user.dir", "."));

	public static void main(String[] args) throws IOException {
		Logger.getLogger().setDebug(true);

		CrashReport.setDirectory(new File(workingDirectory, "crash-reports"));
		Logger.getLogger().setDirectory(new File(workingDirectory, "logs"));

		dbmanager.loadDatabase();
		dbmanager.initDatabase();
		
		final ConfigurationFile configurationFile = Bootstrap.loadProperties("settings.json");
		final TheBox theBox = new TheBox(configurationFile);
		/*
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
		User user = new User("enzo", "pass");
		Logger.getLogger().info(":: " + user.getId() + " ::::: " + usermanager.checkUser("enezo", "paess"));
		
		ServerManager servermanager = ServerManager.getManager();
		
		Thread consoleHandlerThread = new Thread("Console handler") {
			public void run() {
				BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
				String line;
				try {
					while ((line = reader.readLine()) != null)
						ConsoleHandler.handle(theBox, line.trim());
				} catch (IOException e) {
					logger.error("Exception handling console input: %s", e.getLocalizedMessage());
				}
			}
		};
		consoleHandlerThread.setDaemon(true);
		consoleHandlerThread.start();
		Runtime.getRuntime().addShutdownHook(new Thread("Shutdown") {
			@Override
			public void run() {
				try {
					logger.info("Stopping...");
					servermanager.stopServers();
					configurationFile.save();
				} catch (IOException | ServerStopException e) {
					logger.error("Unable to stop TheBox: %s", e.getLocalizedMessage());
				}
			}
		});
		
		MinecraftServer server = servermanager.createServer("Hello World", "C:\\Users\\Enzo\\OneDrive\\Java\\Git\\Box\\servers\\000000\\spigotmc-1.8-R0.1.jar", "enzo", new String[]{"fab", "loius"});
		servermanager.addServer(server);
		try {
			server.start("");
			Logger.getLogger().info(": " + server.isStarted());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}