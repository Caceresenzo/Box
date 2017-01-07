package box;

import java.io.PrintStream;

import box.minecraft.MinecraftServer;
import box.minecraft.ServerManager;

public class ConsoleHandler {
	
	private static final TheBox theBox = TheBox.getInstance();
	private static final ServerManager servermanager = theBox.getServerManager();

	private ConsoleHandler() { }

	private static final PrintStream OUT = System.out;

	public static void handle(TheBox theBox, String line) {
		String[] args = line.contains(" ") ? line.split(" ") : null;
		String avaliable = "";
		
		if (line.startsWith("/")) {
			line = line.substring(1);
		}
		
		switch (line.toLowerCase()) {
			case "help":
				help(false);
				break;
				
			case "list":
				avaliable = "servers";
				if (args[1].length() >= 1 && args[1] != null) {
					error("missing_argument", avaliable);
				}
				
				switch (args[1].toLowerCase()) {
					case "servers":
						StringBuilder builder = new StringBuilder();
						builder.append("\tSTATUS\t\tPORT\t\tNAME");
						for (MinecraftServer server : servermanager.getServers()) {
							builder
								.append("\t|" + (server.isStarted() ? "ON" : "OFF"))
								.append("\t\t| " + server.getPort())
								.append("\t\t| " + server.getName());
						}
						builder.append("\tSTATUS\t\tPORT\t\tNAME");
						break;
					default:
						error("invalid_argument", avaliable);
						break;
				}
				break;
				
			case "stop":
				System.exit(0);
				break;
				
			default:
				help(true);
				break;
		}
	}
	
	private static void help(boolean unknown) {
		final String lineSeparator = System.lineSeparator();
		StringBuilder builder = new StringBuilder();
		if (unknown)
			builder.append("Unknown command. ");
			builder
				.append("List of commands:").append(lineSeparator)
				.append("\t/help : show this message").append(lineSeparator)
				.append("\t/list : get list of data").append(lineSeparator)
				.append("\t/stop");
		OUT.println(builder.toString());
	}
	
	private static void error(String type, String info) {
		String response = "";
		switch (type.toLowerCase()) {
			case "invalid_argument":
				response = "Invalid Argument (Available: " + info + ")";
				break;
			case "missing_argument":
				response = "Missing Argument (Available: " + info + ")";
				break;
			default:
				response = "Error Occured.";
				break;
		}
		response(response);
	}
	
	private static void response(String message) {
		OUT.println(message);
	}

}
