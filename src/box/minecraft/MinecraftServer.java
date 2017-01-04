package box.minecraft;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import box.TheBox;
import box.minecraft.exception.ServerStopException;
import box.minecraft.exception.StartServerException;
import net.sociuris.configuration.ConfigurationSection;
import net.sociuris.logger.Logger;

public class MinecraftServer {

	private static String startCommand = "";

	static {
		ConfigurationSection mcServerSection = TheBox.get().getSettings().getSection("minecraftServer");
		final String lineSeparator = System.lineSeparator();
		StringBuilder builder = new StringBuilder();
		builder.append(mcServerSection.getProperty("javaPath").getAsString()).append(lineSeparator);
		builder.append("bin").append(lineSeparator);
		builder.append("java ");
		builder.append(mcServerSection.getProperty("jvmArguments")).append(' ');
		builder.append("%{JAR_FILE_NAME}").append(' ');
		builder.append(mcServerSection.getProperty("additionalArguments")).append(' ');
	}

	private final Logger logger = Logger.getLogger();
	// private final TheBox theBox;
	private final String name;
	private final String jarFile;

	private final List<String> logs = new ArrayList<String>();

	private Process process;
	private PrintWriter writer;

	public MinecraftServer(TheBox theBox, String name, String jarFile) {
		this.name = name;
		// this.theBox = theBox;
		this.jarFile = jarFile;
	}

	public void start(String additionalArguments) throws Exception {
		if (!isStarted()) {
			ProcessBuilder processBuilder = new ProcessBuilder(
					MinecraftServer.startCommand.replace("%{JAR_FILE_NAME}", jarFile) + additionalArguments);

			logger.debug("Starting server %s...", name);
			this.process = processBuilder.start();
			this.writer = new PrintWriter(process.getOutputStream());

			new Thread("Server input handler") {
				public void run() {
					BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
					String line;
					try {
						while ((line = reader.readLine()) != null)
							appendLog(line);
					} catch (IOException e) {
						logger.error("Exception handling server input: %s", e.getLocalizedMessage());
					}
				}
			}.start();
		} else
			throw new StartServerException("Server already started!");
	}

	public int stop() throws ServerStopException {
		if (isStarted()) {
			logger.info("Stopping server " + name + "...");
			try {
				sendCommand("stop");
				int exitValue = process.waitFor();
				return exitValue;
			} catch (InterruptedException e) {
				this.kill();
				throw new ServerStopException(e.getLocalizedMessage());
			}
		} else
			throw new ServerStopException("Server is not started");
	}

	public void sendCommand(String command) {
		writer.println(command);
		writer.flush();
	}

	public void appendLog(String log) {
		// update web client
		logs.add(log);
		logger.debug("Append log: %s", log);
	}

	public void kill() {
		process.destroy();
	}

	public boolean isStarted() {
		return (process != null) ? process.isAlive() : false;
	}

	public String getName() {
		return name;
	}

}