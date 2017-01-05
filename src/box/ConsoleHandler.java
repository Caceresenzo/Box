package box;

import java.io.PrintStream;

public class ConsoleHandler {

	private ConsoleHandler() {
	}

	private static final PrintStream OUT = System.out;

	public static void handle(TheBox theBox, String line) {
		if (line.startsWith("/"))
			line = line.substring(1);
		
		if (line.equals("stop")) {
			System.exit(0);
		} else {
			final String lineSeparator = System.lineSeparator();
			StringBuilder builder = new StringBuilder();
			if (!line.equalsIgnoreCase("help"))
				builder.append("Unknown command. ");
			builder.append("List of commands:").append(lineSeparator);
			builder.append("\t/help : show this message").append(lineSeparator);
			builder.append("\t/stop");
			OUT.println(builder.toString());
		}
	}

}
