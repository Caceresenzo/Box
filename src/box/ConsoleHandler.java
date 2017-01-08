package box;

import java.io.PrintStream;

import box.minecraft.MinecraftServer;
import box.minecraft.ServerManager;
import box.minecraft.exception.ServerStopException;
import box.utils.NumberUtils;
import box.utils.RandomUtils;

public class ConsoleHandler {

	private static final TheBox THE_BOX = TheBox.getInstance();
	private static final ServerManager SERVER_MANAGER = THE_BOX.getServerManager();

	private ConsoleHandler() {
	}

	private static final PrintStream OUT = System.out;

	public static void handle(TheBox theBox, String rawCommand) {
		if (rawCommand.startsWith("/"))
			rawCommand = rawCommand.substring(1);
		rawCommand = rawCommand.trim();

		String commandName = "";
		String[] commandArgs = new String[0];
		if (rawCommand.contains(" ")) {
			commandName = rawCommand.split(" ", 2)[0].toLowerCase();
			final int substringIndex = commandName.length() + 1;
			if (rawCommand.length() >= substringIndex)
				commandArgs = rawCommand.substring(substringIndex).split(" ");
		} else
			commandName = rawCommand;

		switch (commandName) {
		case "help":
			showHelp();
			break;
		case "stop":
			System.exit(0);
			break;
		case "server":
			if (commandArgs.length > 0) {
				String action = commandArgs[0].toLowerCase();
				switch (action) {
				case "list":
					StringBuilder builder = new StringBuilder();
					final String lineSeparator = System.lineSeparator();
					builder.append("+----------+-------+-----------------------+").append(lineSeparator)
							.append("| STATUT\t| PORT\t| NAME\t\t\t|").append(lineSeparator)
							.append("+---------+-------+-----------------------+").append(lineSeparator);
					for(int i = 0; i < 50; i++) {
						builder.append("| ").append(TheBox.RANDOM.nextInt(1) == 0 ? "STARTED" : "STOPPED").append("  | ")
						.append(TheBox.RANDOM.nextInt(64000)).append(" | ").append(RandomUtils.getRandomString(16))
						.append(lineSeparator);
					}
					/*if (SERVER_MANAGER.hasServers()) {
						for (MinecraftServer server : SERVER_MANAGER.getServers()) {
							builder.append("| ").append(server.isStarted() ? "STARTED" : "STOPPED").append("\t| ")
									.append(server.getPort()).append("\t\t\t\t| ").append(server.getName())
									.append(lineSeparator);
						}
					}
					else
						builder.append("| No server found.\t\t\t  |").append(lineSeparator);*/
					builder.append("+----------+-------+----------------------+");
					print(builder.toString());
					break;
				case "stop":
					if (commandArgs.length > 1) {
						String serverNameOrPort = commandArgs[1];
						MinecraftServer server = SERVER_MANAGER.getServer(serverNameOrPort);
						if (server == null && NumberUtils.isInteger(serverNameOrPort))
							SERVER_MANAGER.getServerByPort(Integer.parseInt(serverNameOrPort));

						if (server != null) {
							if (server.isStarted()) {
								int exitCode = -1;
								String error = "The exit code signal a problem while stopping the server";
								try {
									exitCode = server.stop();
								} catch (ServerStopException e) {
									error = e.getLocalizedMessage();
								}
								if (exitCode != 0)
									printError(ErrorType.GENERIC, "The server exited with an error: " + error
											+ "(exit code: " + exitCode + ")");
								else
									print("The server stopped successfully :)");
							} else
								printError(ErrorType.GENERIC, "The server is not started");
						} else
							printError(ErrorType.GENERIC, "The server was not found");
					} else
						printError(ErrorType.SYNTAX_ERROR, "/server stop <port|name>");
					break;
				default:
					printError(ErrorType.SYNTAX_ERROR, "/server <list|stop>");
					break;
				}
			} else
				printError(ErrorType.SYNTAX_ERROR, "/server <list|stop>");
			break;
		default:
			OUT.print("Unknown command. ");
			showHelp();
			break;
		}

		/*
		 * switch (args[0].toLowerCase()) { case "help": help(false); break;
		 * 
		 * case "server": avaliable = "list, stop"; if (args.length <= 1 ||
		 * args[1] == null) { error("missing_argument", avaliable); break; }
		 * 
		 * switch (args[1].toLowerCase()) { case "list": StringBuilder builder =
		 * new StringBuilder();
		 * builder.append("\tSTATUS\t\tPORT\t\tNAME").append("\n"); if
		 * (!servermanager.getServers().isEmpty()) { for (MinecraftServer server
		 * : servermanager.getServers()) { builder.append("\t| " +
		 * (server.isStarted() ? "ON" : "OFF")).append("\t\t| " +
		 * server.getPort()) .append("\t\t| " + server.getName()).append("\n");
		 * } } else { builder.append("\t| -/-").append("\t\t| 00000").
		 * append("\t\t| No Server Avaliable").append("\n"); } //
		 * builder.append("\tSTATUS\t\tPORT\t\tNAME");
		 * response(builder.toString()); break;
		 * 
		 * case "stop": if (args.length <= 2 || args[2] == null) {
		 * error("need_more_argument", "<Server Port>"); break; } if
		 * (!NumberUtils.isInteger(args[2])) { error("invalid_integer",
		 * "Do \"/server list\" to see server's port"); break; } Integer port =
		 * Integer.parseInt(args[2]); MinecraftServer server =
		 * servermanager.getServerByPort(port); if (server == null) {
		 * error("server_not_exists", ""); break; } if (server != null &&
		 * !server.isStarted()) { error("server_not_started", ""); break; } if
		 * (!servermanager.stopServerByPort(port)) {
		 * error("server_fail_on_stop", ""); break; } else {
		 * response("---------- Server Stopped!"); break; } // break;
		 * //Unreachable code
		 * 
		 * default: error("invalid_argument", avaliable); break; } break;
		 * 
		 * case "stop": System.exit(0); break;
		 * 
		 * default: OUT.print("Unknown command. "); showHelp(); break; }
		 */
	}

	private static void showHelp() {
		final String lineSeparator = System.lineSeparator();
		StringBuilder builder = new StringBuilder();
		builder.append("List of commands:").append(lineSeparator).append("\t/help : show this message")
				.append(lineSeparator).append("\t/server :").append(lineSeparator).append("\t\tlist : get list of data")
				.append(lineSeparator).append("\t\tstop <Server Port> : stop a server by the port")
				.append(lineSeparator).append("\t/stop");
		OUT.println(builder.toString());
	}

	private static void print(String message, Object... args) {
		OUT.println(String.format(message, args));
	}

	private static void printError(ErrorType errorType, Object... args) {
		OUT.println(errorType.getMessage(args));
	}

	/*
	 * private static void error(String type, String info) { switch
	 * (type.toLowerCase()) { case "invalid_argument":
	 * send("Invalid Argument. Available: %s", info); break; case
	 * "missing_argument": send("Missing Argument. Available: %s", info); break;
	 * case "need_more_argument": send("Need More Argument. Add: %s", info);
	 * break; case "invalid_integer": send("This value is not an Integer (%s)",
	 * info); break; case "server_not_started":
	 * send("This server is not started."); break; case "server_not_exists":
	 * send("This server does not exists."); break; case "server_fail_on_stop":
	 * send("This server has not been stopped. An error occured."); break;
	 * default: send("Error Occured."); break; } }
	 */

	private static enum ErrorType {

		INVALID_ARGUMENTS("Invalid Argument. Availables: %s"), SYNTAX_ERROR("Usage: %s"), GENERIC("Error: %s");

		private final String message;

		private ErrorType(String message) {
			this.message = message;
		}

		private String getMessage(Object... args) {
			return String.format(message, args);
		}

	}

}
