package box;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import box.minecraft.exception.ServerStopException;
import net.sociuris.configuration.ConfigurationFile;
import net.sociuris.configuration.ConfigurationSection;
import net.sociuris.crash.CrashReport;
import net.sociuris.logger.Logger;

public class Bootstrap {

	private static final Logger LOGGER = Logger.getLogger();
	private static File workingDirectory = new File(System.getProperty("user.dir", "."));

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

				LOGGER.debug("arg[key=%s,value=%s]", key, value);

				switch (key) {
				case "debug":
					LOGGER.setDebug(true);
					break;
				case "workingDirectory":
					workingDirectory = new File(value);
					if (!workingDirectory.exists())
						workingDirectory.mkdirs();
					break;
				case "settingsFile":
					settingsFile = value;
					break;
				default:
					LOGGER.debug("Invalid argument: %s", arg);
					break;
				}
			}

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

			Runtime.getRuntime().addShutdownHook(new Thread("Shutdown Thread") {
				@Override
				public void run() {
					try {
						LOGGER.info("Stopping...");
						theBox.stopServers();
						configurationFile.save();
					} catch (IOException | ServerStopException e) {
						LOGGER.error("Unable to stop TheBox: %s", e.getLocalizedMessage());
					}
				}
			});
		} catch (IOException e) {
			CrashReport.makeCrashReport("Unable to start TheBox!", e);
		}
	}

	public static ConfigurationFile loadProperties(String settingsFilePath) throws IOException {
		final File settingsFile = new File(workingDirectory, settingsFilePath);
		if (!settingsFile.exists())
			settingsFile.createNewFile();

		final ConfigurationFile settings = new ConfigurationFile(settingsFile);

		final ConfigurationSection minecraftServerSection = settings.getSection("minecraftServer");
		minecraftServerSection.addDefaultProperty("javaPath", System.getProperty("java.home"));
		minecraftServerSection.addDefaultProperty("jvmArguments", "");
		minecraftServerSection.addDefaultProperty("additionalArguments", "-Xmx1G");

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