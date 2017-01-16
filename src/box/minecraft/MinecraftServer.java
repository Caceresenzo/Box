package box.minecraft;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import box.TheBox;
import box.minecraft.exception.ServerStopException;
import box.minecraft.exception.StartServerException;
import box.minecraft.text.TextComponent;
import box.util.FileUtils;
import net.sociuris.configuration.ConfigurationSection;
import net.sociuris.logger.Logger;

public class MinecraftServer {

	private static final String START_COMMAND = getStartCommand();

	private final Logger logger = Logger.getLogger();
	private final Runtime runtime = Runtime.getRuntime();

	private final int port;
	private final String name;
	private final String jarFile;

	private final String owner;
	private final List<String> operators = new ArrayList<String>();

	private final List<String> logs = new ArrayList<String>();

	private Process process;
	private PrintWriter writer;

	/**
	 * Create a new Instance
	 * 
	 * @param theBox
	 * @param port
	 * @param name
	 * @param jarFile
	 * @param owner
	 * @param operators
	 */
	protected MinecraftServer(TheBox theBox, int port, String name, String jarFile, String owner,
			List<String> operators) {
		this.port = port;
		this.name = name;
		this.jarFile = jarFile;
		this.owner = owner;
		this.operators.addAll(operators);
	}

	/**
	 * Create a new Instance but without the Operators
	 */
	protected MinecraftServer(TheBox theBox, int port, String name, String jarFile, String owner) {
		this(theBox, port, owner, owner, owner, new ArrayList<String>());
	}

	/**
	 * Start the Server
	 * 
	 * @param additionalArguments
	 * @throws Exception
	 */
	public void start(String additionalArguments) throws Exception {
		if (!isStarted()) {
			String finishedStartCommand = MinecraftServer.START_COMMAND.replace("%{JAR_FILE_NAME}", jarFile)
					+ additionalArguments + " --port " + port;

			File serverWorkingDirectory = new File(TheBox.PROPERTIES.getSection("minecraftServer")
					.getValue("workingServerDirectory").getAsString().replace("%port%", String.valueOf(port)));
			File serverEulaFile = new File(serverWorkingDirectory.getAbsolutePath() + "\\eula.txt");
			FileUtils.createDefaultDirectory(serverWorkingDirectory);
			FileUtils.writeInFile(serverEulaFile, new String[] { "eula=true" });

			logger.debug("Starting server %s...", name);
			this.process = runtime.exec(finishedStartCommand, new String[] { "" }, serverWorkingDirectory);
			this.writer = new PrintWriter(process.getOutputStream());

			new Thread("Server Input Handler") {
				public void run() {
					BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
					String line;
					try {
						while ((line = reader.readLine()) != null) {
							appendLog(line);
						}
					} catch (IOException exception) {
						logger.error("Exception handling server input: %s", exception.getLocalizedMessage());
					}
				}
			}.start();
		} else {
			throw new StartServerException("Server already started!");
		}
	}

	/**
	 * Stop the Server
	 * 
	 * @return the exit code value
	 * @throws ServerStopException
	 */
	public int stop() throws ServerStopException {
		if (isStarted()) {
			logger.info("Stopping server " + name + "...");
			try {
				sendCommand("stop");
				Integer exitValue = process.waitFor();
				return exitValue;
			} catch (InterruptedException exception) {
				this.kill();
				throw new ServerStopException(exception.getLocalizedMessage());
			}
		} else {
			return -1;
		}
	}

	/**
	 * Send a command to the server
	 * 
	 * @param command
	 */
	public void sendCommand(String command) {
		writer.println(command);
		writer.flush();
	}

	/**
	 * Send a message to the server
	 * 
	 * @param message
	 */
	public void sendMessage(String message) {
		writer.println(
				"tellraw @a [{\"text\":\"TheBox\",\"color\":\"white\"},{\"text\":\" > \",\"color\":\"gray\"},{\"text\":\""
						+ message + "\",\"color\":\"white\"}]");
		writer.flush();
	}

	public void sendFormattedMessage(TextComponent textComponent) {
		writer.println("tellraw @a " + textComponent.toJsonObject().toString());
		writer.flush();
	}

	/**
	 * Send the server output to the console
	 * 
	 * @param log
	 */
	public void appendLog(String log) {
		// update web client
		logs.add(log);
		logger.debug("Append log: %s", log);
	}

	/**
	 * Destroy the server process
	 */
	public void kill() {
		process.destroy();
	}

	/**
	 * Check if server is started
	 * 
	 * @return {@code true} if the server is started, {@code false} otherwise
	 */
	public boolean isStarted() {
		return (process != null) ? process.isAlive() : false;
	}

	/**
	 * Get the server's port
	 * 
	 * @return The server's port
	 */
	public int getPort() {
		return port;
	}

	/**
	 * Get the name of the server
	 * 
	 * @return server's name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Get the server's owner
	 * 
	 * @return owner's name
	 */
	public String getOwner() {
		return owner;
	}

	/**
	 * Get all of the operators who have access to this server
	 * 
	 * @return All of the operators
	 */
	public List<String> getOperators() {
		return operators;
	}

	/**
	 * Initialize the start command
	 * 
	 * @return The start command
	 */
	private static String getStartCommand() {
		final String lineSeparator = File.separator;
		ConfigurationSection mcServerSection = TheBox.PROPERTIES.getSection("minecraftServer");
		StringBuilder builder = new StringBuilder();
		builder.append("\"" + mcServerSection.getValue("javaPath").getAsString()).append(lineSeparator).append("bin")
				.append(lineSeparator).append("java.exe\"").append(' ')
				.append(mcServerSection.getValue("jvmArguments").getAsString()).append(' ')
				.append("-jar \"%{JAR_FILE_NAME}\"").append(' ')
				.append(mcServerSection.getValue("additionalArguments").getAsString()).append(' ');
		return builder.toString();
	}

}