package box;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Random;
import java.util.regex.Pattern;

import box.database.DatabaseManager;
import box.gui.TheBoxGui;
import box.minecraft.ServerManager;
import box.minecraft.exception.ServerStopException;
import box.web.WebPageApi;
import box.web.WebPagePanel;
import net.sociuris.configuration.ConfigurationFile;
import net.sociuris.configuration.ConfigurationSection;
import net.sociuris.logger.Logger;
import net.sociuris.web.WebSite;

public class TheBox {

	public static final String VERSION = "0.1b";
	public static ConfigurationFile PROPERTIES = null;
	public static final Random RANDOM = new Random();

	private static TheBox instance;

	public static TheBox getInstance() {
		return TheBox.instance;
	}

	private final Logger logger = Logger.getLogger();
	private final TheBoxGui boxGui;
	private final WebSite webSite;
	private final ServerManager serverManager;
	private final DatabaseManager databaseManager;

	public TheBox(ConfigurationFile configurationFile) {
		TheBox.PROPERTIES = configurationFile;

		if (configurationFile.getProperty("useGui").getAsBoolean() || System.console() == null) {
			this.boxGui = TheBoxGui.createGui();
			if(!TheBoxGui.hasGui())
				logger.warn("You try to show the GUI but you system doesn't support it!");
		}
		else
			this.boxGui = null;

		ConfigurationSection webServerSection = configurationFile.getSection("webServer");
		this.webSite = new WebSite(webServerSection.getProperty("ipAddress").getAsString(),
				webServerSection.getProperty("port").getAsInteger());
		this.webSite.start();

		this.webSite.addPage(Pattern.compile("/?"), new WebPagePanel());
		this.webSite.addPage(Pattern.compile("/api/(\\w)*"), new WebPageApi());

		this.serverManager = new ServerManager();
		this.databaseManager = new DatabaseManager();

		TheBox.instance = this;
	}

	public void stop() throws Exception {
		Exception exception = null;
		logger.info("Stopping...");
		webSite.stop();

		try {
			serverManager.stopServers();
		} catch (ServerStopException e) {
			exception = e;
		}

		try {
			databaseManager.closeConnections();
		} catch (SQLException e) {
			exception = e;
		}

		if (TheBoxGui.hasGui()) {
			try {
				boxGui.stopProperly();
			} catch (Exception e) {
				exception = e;
			}
		}

		try {
			PROPERTIES.save();
		} catch (IOException e) {
			exception = e;
		}

		if (exception != null)
			throw exception;
	}

	public WebSite getWebSite() {
		return webSite;
	}

	public ServerManager getServerManager() {
		return serverManager;
	}

}