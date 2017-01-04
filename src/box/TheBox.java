package box;

import box.web.WebServer;
import net.sociuris.configuration.ConfigurationFile;

public class TheBox {

	private static TheBox instance;

	public static TheBox get() {
		return TheBox.instance;
	}

	//private final Logger logger = Logger.getLogger();
	private final ConfigurationFile settings;
	private final WebServer webServer;

	public TheBox(ConfigurationFile configurationFile) {
		this.settings = configurationFile;
		
		this.webServer = new WebServer(configurationFile.getSection("webServer"));
		this.webServer.start();

		TheBox.instance = this;
	}

	public void stopServers() {
		
	}
	
	public ConfigurationFile getSettings() {
		return settings;
	}

	public WebServer getWebServer() {
		return webServer;
	}

}