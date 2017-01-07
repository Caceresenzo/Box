package box.minecraft;

import java.util.ArrayList;
import java.util.List;

import box.TheBox;
import box.minecraft.exception.ServerStopException;
import box.utils.NetUtils;
import net.sociuris.logger.Logger;

public class ServerManager {

	private final Logger logger = Logger.getLogger();
	
	private final List<MinecraftServer> serverList = new ArrayList<MinecraftServer>();

	public ServerManager() {
		for (MinecraftServer server : serverList) {
			try {
				if (server.isStarted()) {
					server.sendCommand("tellraw @a [\"\",{\"text\":\"[Box] \",\"color\":\"blue\",\"bold\":true,\"clickEvent\":{\"action\":\"open_url\",\"value\":\"\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"The Box™\"}]}}},{\"text\":\"Box's ServerManager instance recreating.... Stopping Server...\",\"color\":\"none\",\"bold\":false}]");
					server.sendCommand("save all");
					server.stop();
				}
			} catch (ServerStopException exception) {
				exception.printStackTrace();
			}
		}
	}

	public MinecraftServer createServer(String name, Short port, String jarFilePath, String owner, List<String> operators) {
		if (port == -1 || port < 1 || port > 65534) {
			port = NetUtils.getAvailablePort();
		}
		MinecraftServer minecraftServer = new MinecraftServer(TheBox.getInstance(), port, name, jarFilePath, owner, operators);
		if (!serverList.contains(minecraftServer)) {
			logger.info("Creating Minecraft server: %s", minecraftServer.toString());
			serverList.add(minecraftServer);
			return minecraftServer;
		} else {
			return getServer(name);
		}
	}

	public Boolean removeServer(MinecraftServer server) {
		if (!server.isStarted()) {
			return serverList.remove(server);
		}
		return false;
	}

	public MinecraftServer getServer(String name) {
		for (MinecraftServer server : serverList) {
			if (server.getName().equals(name)) {
				return server;
			}
		}
		return null;
	}

	public Boolean hasServers() {
		return !serverList.isEmpty();
	}

	public List<MinecraftServer> getServers() {
		return serverList;
	}

	public void stopServers() throws ServerStopException {
		ServerStopException stopException = null;
		for (MinecraftServer server : serverList) {
			try {
				server.stop();
			} catch (ServerStopException exception) {
				stopException = exception;
			}
		}
		if (stopException != null) {
			throw stopException;
		}
	}

}