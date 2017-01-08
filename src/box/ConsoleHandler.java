package box;

import java.io.PrintStream;

import box.minecraft.MinecraftServer;
import box.minecraft.ServerManager;
import box.utils.ObjectUtils;

public class ConsoleHandler {
	
	private static final TheBox theBox = TheBox.getInstance();
	private static final ServerManager servermanager = theBox.getServerManager();

	private ConsoleHandler() { }

	private static final PrintStream OUT = System.out;

	public static void handle(TheBox theBox, String line) {
		if (line.startsWith("/")) {
			line = line.substring(1);
		}
		
		String[] args = line.contains(" ") ? line.split(" ") : new String[]{line};
		String avaliable = "";
		
		switch (args[0].toLowerCase()) {
			case "help":
				help(false);
				break;
				
			case "server":
				avaliable = "list, stop";
				if (args.length <= 1 || args[1] == null) {
					error("missing_argument", avaliable); break;
				}
				
				switch (args[1].toLowerCase()) {
					case "list":
						StringBuilder builder = new StringBuilder();
						builder.append("\tSTATUS\t\tPORT\t\tNAME").append("\n");
						if (!servermanager.getServers().isEmpty()) {
							for (MinecraftServer server : servermanager.getServers()) {
								builder
									.append("\t| " + (server.isStarted() ? "ON" : "OFF"))
									.append("\t\t| " + server.getPort())
									.append("\t\t| " + server.getName())
									.append("\n");
							}
						} else {
							builder
							.append("\t| -/-").append("\t\t| 00000").append("\t\t| No Server Avaliable").append("\n");
						}
						//builder.append("\tSTATUS\t\tPORT\t\tNAME");
						response(builder.toString());
						break;
						
					case "stop":
						if (args.length <= 2 || args[2] == null) {
							error("need_more_argument", "<Server Port>"); break;
						}
						if(!ObjectUtils.isStringInteger(args[2])) {
							error("invalid_integer", "Do \"/server list\" to see server's port"); break;
						}
						Integer port = Integer.parseInt(args[2]);
						MinecraftServer server = servermanager.getServerByPort(port);
						if (server == null) {
							error("server_not_exists", ""); break;
						}
						if (server != null && !server.isStarted()) {
							error("server_not_started", ""); break;
						}
						if (!servermanager.stopServerByPort(port)) {
							error("server_fail_on_stop", ""); break;
						} else {
							response("---------- Server Stopped!"); break;
						}
						//break; //Unreachable code
						
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
				.append("\t/server :").append(lineSeparator)
				.append("\t\tlist : get list of data").append(lineSeparator)
				.append("\t\tstop <Server Port> : stop a server by the port").append(lineSeparator)
				.append("\t/stop");
		OUT.println(builder.toString());
	}
	
	private static void error(String type, String info) {
		String response = "";
		switch (type.toLowerCase()) {
			case "invalid_argument":
				response = "Invalid Argument. Available: " + info;
				break;
			case "missing_argument":
				response = "Missing Argument. Available: " + info;
				break;
			case "need_more_argument":
				response = "Need More Argument. Add: " + info;
				break;
			case "invalid_integer":
				response = "This value is not an Integer (" + info + ")";
				break;
			case "server_not_started":
				response = "This server is not started.";
				break;
			case "server_not_exists":
				response = "This server does not exists.";
				break;
			case "server_fail_on_stop":
				response = "This server has not been stopped. An error occured.";
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
