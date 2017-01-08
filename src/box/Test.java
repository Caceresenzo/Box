package box;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;

import box.database.Database;
import box.database.DatabaseManager;
import box.minecraft.MinecraftServer;
import box.minecraft.ServerManager;
import box.minecraft.exception.ServerStopException;
import box.utils.CollectionUtils;
import box.utils.RandomUtils;
import box.web.user.UserManager;
import net.sociuris.configuration.ConfigurationFile;
import net.sociuris.crash.CrashReport;
import net.sociuris.logger.Logger;

public class Test {
	
	private static ConfigurationFile configurationFile = null;
	static {
		try { configurationFile = Bootstrap.loadProperties("settings.json"); } catch (IOException exception) { }
	}
	private static final TheBox theBox = new TheBox(configurationFile);
	
	private static final Logger logger = Logger.getLogger();

	private static DatabaseManager dbmanager = theBox.getDatabaseManager();
	private static ServerManager servermanager = theBox.getServerManager();
	private static UserManager usermanager = theBox.getUserManager();
	private static File workingDirectory = new File(System.getProperty("user.dir", "."));

	public static void main(String[] args) throws IOException {
		
		String randomString = RandomUtils.getRandomString(10);
		logger.info("Random String: " + randomString);
		
		TEST_INIT();
		
		TEST_DATABASE_INIT();
		
		//TEST_DATABASE_INSERT(DatabaseManager.WEB, "INSERT INTO 'web_sessions' ('session_id', 'session_ip', 'session_token') VALUES ('%random%', '90.19.73.219', '%random%')".replace("%random%", randomString));
		
		THREAD_CONSOLE_HANDLER();
		THREAD_SHUTDOWN_HOOK();
		
		/*
		 *  TEST ZONE
		 */
		String jarPath = "C:\\Users\\Enzo\\Desktop\\box\\servers\\000000\\spigotmc-1.8-R0.1.jar";
		MinecraftServer server = servermanager.createServer("Hello World", (short) 25565, jarPath, "enzo", CollectionUtils.arrayToList(new String[]{"fab", "loius"}));
		MinecraftServer server2 = servermanager.createServer("Salut tout le monde", (short) 25564, jarPath, "enzo", CollectionUtils.arrayToList(new String[]{"fab", "loius"}));
		MinecraftServer server3 = servermanager.createServer("Noob Games", (short) 25562, jarPath, "enzo", CollectionUtils.arrayToList(new String[]{"fab", "loius"}));
		
		try {
			server.start("");
			Logger.getLogger().info(": " + server.isStarted());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	/**
	 * 
	 */
	public static void TEST_INIT() {
		Logger.getLogger().setDebug(true);

		CrashReport.setDirectory(new File(workingDirectory, "crash-reports"));
		Logger.getLogger().setDirectory(new File(workingDirectory, "logs"));
	}
	
	/**
	 * 
	 */
	public static void TEST_DATABASE_INIT() {
		dbmanager.loadDatabase();
		dbmanager.initDatabase();
	}
	
	/**
	 * 
	 * @param database
	 * @param sql
	 */
	public static void TEST_DATABASE_INSERT(Database database, String sql) {
		try {
			database.executeUpdate(sql);
		} catch (SQLException exception) {
			exception.printStackTrace();
		}
	}
	
	/**
	 * 
	 */
	public static void THREAD_CONSOLE_HANDLER() {
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
	}
	
	/**
	 * 
	 */
	public static void THREAD_SHUTDOWN_HOOK() {
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
	}

}