package box.minecraft;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import box.Bootstrap;
import box.TheBox;
import box.minecraft.exception.ServerStopException;
import box.minecraft.exception.StartServerException;
import box.utils.FileUtils;
import box.utils.RandomUtils;
import net.sociuris.configuration.ConfigurationSection;
import net.sociuris.logger.Logger;

public class MinecraftServer {
	
	private static final String startCommand = getStartCommand();

	private static String getStartCommand() {
		ConfigurationSection mcServerSection = TheBox.PROPERTIES.getSection("minecraftServer");
		final String lineSeparator = File.separator;
		StringBuilder builder = new StringBuilder();
		builder.append("\"" + mcServerSection.getProperty("javaPath").getAsString()).append(lineSeparator);
		builder.append("bin").append(lineSeparator);
		builder.append("java.exe\"");
		builder.append(" " + mcServerSection.getProperty("jvmArguments").getAsString()).append(' ');
		builder.append("-jar \"%{JAR_FILE_NAME}\"").append(' ');
		builder.append("" + mcServerSection.getProperty("additionalArguments").getAsString()).append(' ');
		return builder.toString();
	}

	private final Logger logger = Logger.getLogger();
	// private final TheBox theBox;
	private final String name;
	private final String jarFile;
	
	private final Runtime runtime = Runtime.getRuntime();

	private final List<String> logs = new ArrayList<String>();

	private Process process;
	private PrintWriter writer;
	

	private String owner;
	private String[] subowner;
	
	public MinecraftServer(TheBox theBox, String name, String jarFile, String owner, String[] subowner) {
		this.name = name;
		// this.theBox = theBox;
		this.jarFile = jarFile;
		this.owner = owner;
		this.subowner = subowner;
	}
	
	public MinecraftServer(TheBox theBox, String name, String jarFile, String owner) {
		this(theBox, owner, owner, owner, null);
	}

	public void start(String additionalArguments) throws Exception {
		if (!isStarted()) {
			Integer port = RandomUtils.getRandomInt(25500, 25600);
			String finishedStartCommand = MinecraftServer.startCommand.replace("%{JAR_FILE_NAME}", jarFile) + additionalArguments + " --port " + port;
			System.out.println(":: " + finishedStartCommand + "\n PORT: " + port);
			//ProcessBuilder processBuilder = new ProcessBuilder(finishedStartCommand);
			File serverWorkingDirectory = new File(Bootstrap.getWorkingDirectory().getAbsolutePath() + "\\servers\\" + port);
			File serverEulaFile = new File(serverWorkingDirectory.getAbsolutePath() + "\\eula.txt");
			FileUtils.createDirectoryIfNotExists(serverWorkingDirectory);
			if (!serverEulaFile.exists()) {
				serverEulaFile.createNewFile();
				BufferedWriter writer = new BufferedWriter(new FileWriter(serverEulaFile));
				writer.write("eula=true");
				writer.close();
			}
			Process process = runtime.exec(finishedStartCommand, new String[]{""}, serverWorkingDirectory);
			logger.debug("Starting server %s...", name);
			this.process = process;
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
		}
		else
			return -1;
	}

	public void sendCommand(String command) {
		writer.println(command);
		writer.flush();
	}

	public void sendMessage(String message) {
		writer.println("tell @a [Box]" + message);
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
	
	public String getOwner() {
		return owner;
	}
	
	public String[] getSubOwner() {
		return subowner;
	}

}