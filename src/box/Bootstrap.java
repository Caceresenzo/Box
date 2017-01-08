package box;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import box.util.FileUtils;
import net.sociuris.configuration.ConfigurationFile;
import net.sociuris.configuration.ConfigurationSection;
import net.sociuris.crash.CrashReport;
import net.sociuris.logger.Logger;

public class Bootstrap {

	private static final Logger LOGGER = Logger.getLogger();
	private static File workingDirectory = new File(System.getProperty("user.dir", "."));

	public static File getWorkingDirectory() {
		return workingDirectory;
	}

	public static void main(String[] args) {
		try {
			Thread.currentThread().setName("Main");

			String settingsFile = "settings.json";

			for (String arg : args) {
				if (arg.startsWith("-"))
					arg = arg.substring(1);
				String key = arg;
				String value = arg;

				if (arg.contains("=")) {
					String[] splitArg = arg.split("=", 2);
					key = splitArg[0];
					if (splitArg.length == 2)
						value = splitArg[1];
				}

				switch (key) {
				case "debug":
					LOGGER.setDebug(true);
					break;
				case "workingDirectory":
					workingDirectory = new File(value);
					break;
				case "settingsFile":
					settingsFile = value;
					break;
				default:
					break;
				}
			}

			FileUtils.createDefaultDirectory(workingDirectory);

			File crashReportDir = new File(workingDirectory, "crash-reports");
			File logsDir = new File(workingDirectory, "logs");

			FileUtils.createDefaultDirectory(new File(workingDirectory, "databases"));
			FileUtils.createDefaultDirectory(crashReportDir);
			FileUtils.createDefaultDirectory(logsDir);

			CrashReport.setDirectory(crashReportDir);
			LOGGER.setDirectory(logsDir);

			final ConfigurationFile configurationFile = Bootstrap.loadProperties(settingsFile);
			final TheBox theBox = new TheBox(configurationFile);

			Thread consoleHandlerThread = new Thread("Console handler") {
				public void run() {
					BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
					String line;
					try {
						while ((line = reader.readLine()) != null)
							ConsoleHandler.handle(theBox, line.trim());
					} catch (IOException e) {
						LOGGER.error("Exception handling console input: %s", e.getLocalizedMessage());
					}
				}
			};
			consoleHandlerThread.setDaemon(true);
			consoleHandlerThread.start();

			Runtime.getRuntime().addShutdownHook(new Thread("Shutdown") {
				@Override
				public void run() {
					try {
						theBox.stop();
					} catch (Exception e) {
						CrashReport.makeCrashReport("Unable to stop correctly TheBox", e);
					}
				}
			});

		} catch (Exception e) {
			CrashReport.makeCrashReport("Unable to start TheBox!", e);
		}
	}

	public static ConfigurationFile loadProperties(String settingsFilePath) throws IOException {
		final File settingsFile = new File(workingDirectory, settingsFilePath);
		if (!settingsFile.exists())
			settingsFile.createNewFile();

		final ConfigurationFile settings = new ConfigurationFile(settingsFile);
		settings.addDefaultProperty("useGui", true);

		final ConfigurationSection minecraftServerSection = settings.getSection("minecraftServer");
		minecraftServerSection.addDefaultProperty("javaPath", System.getProperty("java.home"));
		minecraftServerSection.addDefaultProperty("jvmArguments", "-Xmx1G");
		minecraftServerSection.addDefaultProperty("additionalArguments", "");

		final ConfigurationSection webServerSection = settings.getSection("webServer");
		webServerSection.addDefaultProperty("enable", true);
		webServerSection.addDefaultProperty("ipAddress", "127.0.0.1");
		webServerSection.addDefaultProperty("port", 8080);

		final ConfigurationSection socketServerSection = settings.getSection("socketServer");
		socketServerSection.addDefaultProperty("enable", true);
		socketServerSection.addDefaultProperty("ipAddress", "127.0.0.1");
		socketServerSection.addDefaultProperty("port", 32768);

		settings.save();

		return settings;
	}

}