package box;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import net.sociuris.configuration.ConfigurationFile;
import net.sociuris.configuration.ConfigurationSection;
import net.sociuris.logger.Logger;

public class Bootstrap {

	private static final Logger LOGGER = Logger.getLogger();

	public static void main(String[] args) {
		try {
			Thread.currentThread().setName("Main");

			String settingsFile = "settings.json";

			for (String arg : args) {
				if (arg.startsWith("-"))
					arg = arg.substring(1);
				String key = arg, value = arg;
				if (arg.contains("=")) {
					String[] splitArg = arg.split("=", 1);
					key = splitArg[0];
					if (splitArg.length == 2)
						value = splitArg[1];
				}
				LOGGER.debug("arg[key=%s,value=%s]", key, value);
				switch (key) {
				case "debug":
					LOGGER.setDebug(true);
					break;
				case "settings-file":
					settingsFile = value;
					break;
				default:
					LOGGER.warn("Invalid argument: %s", arg);
					break;
				}
			}

			TheBox theBox = new TheBox(Bootstrap.loadProperties(settingsFile));

			Thread consoleHandlerThread = new Thread("Console handler") {
				public void run() {
					BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
					String line;

					try {
						while ((line = reader.readLine()) != null) {
							LOGGER.info("Input line: %s", line);
							// process console command
						}
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
					theBox.stopServers();
				}
			});
		} catch (IOException e) {
			LOGGER.error("Unable to start TheBox: %s", e.getLocalizedMessage());
		}
	}

	public static ConfigurationFile loadProperties(String settingsFilePath) throws IOException {
		final File settingsFile = new File(settingsFilePath);

		if (!settingsFile.exists())
			settingsFile.createNewFile();

		final ConfigurationFile settings = new ConfigurationFile(settingsFile);

		final ConfigurationSection webServerSection = settings.getSection("webServer");
		webServerSection.addDefaultProperty("ipAddress", "127.0.0.1");
		webServerSection.addDefaultProperty("port", 8080);

		return settings;
	}

}