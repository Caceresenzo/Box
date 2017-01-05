package box;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import box.minecraft.MinecraftServer;
import box.minecraft.exception.ServerStopException;
import box.web.WebServer;
import net.sociuris.configuration.ConfigurationFile;

public class TheBox {

	public static final String VERSION = "0.1b";
	
	private static TheBox instance;

	public static TheBox get() {
		return TheBox.instance;
	}

	// private final Logger logger = Logger.getLogger();
	private final ConfigurationFile settings;
	private final WebServer webServer;
	private final Map<String, MinecraftServer> minecraftServerMap = new HashMap<String, MinecraftServer>();

	public TheBox(ConfigurationFile configurationFile) {
		this.settings = configurationFile;

		this.webServer = new WebServer(configurationFile.getSection("webServer"));
		this.webServer.start();

		TheBox.instance = this;
	}

	public void stopServers() throws ServerStopException {
		for(Entry<String, MinecraftServer> entry : minecraftServerMap.entrySet())
			entry.getValue().stop();
	}
	
	public MinecraftServer getServer(String name) {
		return minecraftServerMap.get(name);
	}
	
	public void removeServer(MinecraftServer minecraftServer) {
		if(!minecraftServer.isStarted())
			minecraftServerMap.remove(minecraftServer);
	}
	
	public MinecraftServer createServer(String name, String jarFile) {
		MinecraftServer minecraftServer = new MinecraftServer(this, name, jarFile);
		minecraftServerMap.put(minecraftServer.getName(), minecraftServer);
		return minecraftServer;
	}

	public ConfigurationFile getSettings() {
		return settings;
	}

	public WebServer getWebServer() {
		return webServer;
	}

}