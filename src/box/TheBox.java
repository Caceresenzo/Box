package box;

import box.web.WebServer;
import net.sociuris.configuration.ConfigurationFile;

public class TheBox {

	private static TheBox instance;
	public static TheBox get() {
		return TheBox.instance;
	}
	
	private final WebServer webServer;
	
	public TheBox(ConfigurationFile configurationFile) {
		TheBox.instance = this;
		this.webServer = new WebServer(configurationFile.getSection("webServer"));
	}
	
	public WebServer getWebServer() {
		return webServer;
	}

	public void stopServers() {
		
	}
	
}