package box;

import java.io.IOException;
import java.sql.SQLException;

import box.database.DatabaseManager;
import box.gui.TheBoxGui;
import box.minecraft.ServerManager;
import box.minecraft.exception.ServerStopException;
import box.web.WebPageApi;
import box.web.WebPagePanel;
import box.web.user.UserManager;
import net.sociuris.configuration.ConfigurationFile;
import net.sociuris.configuration.ConfigurationSection;
import net.sociuris.logger.Logger;
import net.sociuris.web.WebSite;

public class TheBox {

	public static final String VERSION = "0.1b";
	public static ConfigurationFile PROPERTIES = null;

	private static TheBox instance;

	public static TheBox getInstance() {
		return TheBox.instance;
	}

	private final Logger logger = Logger.getLogger();
	private final TheBoxGui boxGui;
	private final WebSite webSite;
	private final DatabaseManager databaseManager;
	private final UserManager userManager;
	private final ServerManager serverManager;

	public TheBox(ConfigurationFile configurationFile) {
		TheBox.PROPERTIES = configurationFile;

		if (configurationFile.getProperty("useGui").getAsBoolean() || System.console() == null) {
			this.boxGui = TheBoxGui.createGui();
			if (!TheBoxGui.hasGui())
				logger.warn("You try to create GUI but you system doesn't support it!");
		} else
			this.boxGui = null;

		ConfigurationSection webServerSection = configurationFile.getSection("webServer");
		this.webSite = new WebSite(webServerSection.getProperty("ipAddress").getAsString(),
				webServerSection.getProperty("port").getAsInteger());
		this.webSite.start();

		this.webSite.addPage("/?", new WebPagePanel());
		this.webSite.addPage("/api/(\\w)*", new WebPageApi());

		this.userManager = new UserManager();

		this.databaseManager = new DatabaseManager();
		this.databaseManager.loadDatabase();
		this.databaseManager.initDatabase();

		this.serverManager = new ServerManager();

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

	public DatabaseManager getDatabaseManager() {
		return databaseManager;
	}

	public UserManager getUserManager() {
		return userManager;
	}

	public ServerManager getServerManager() {
		return serverManager;
	}

}