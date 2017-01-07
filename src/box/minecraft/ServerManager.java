package box.minecraft;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

import box.TheBox;
import box.minecraft.exception.ServerStopException;
import box.web.users.User;
import net.sociuris.logger.Logger;

public class ServerManager {

	private final Logger logger = Logger.getLogger();
	private final List<MinecraftServer> serverList = new ArrayList<MinecraftServer>();

	public ServerManager() {
		for (MinecraftServer server : serverList) {
			try {
				if (server.isStarted()) {
					server.sendMessage(
							"tellraw @a [\"\",{\"text\":\"[Box] \",\"color\":\"blue\",\"bold\":true,\"clickEvent\":{\"action\":\"open_url\",\"value\":\"\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"The Box™\"}]}}},{\"text\":\"Box's ServerManager instance recreating.... Stopping Server...\",\"color\":\"none\",\"bold\":false}]");
					server.sendCommand("save all");
					server.stop();
				}
			} catch (ServerStopException exception) {
				exception.printStackTrace();
			}
		}
	}

	public MinecraftServer createServer(String name, int port, String jarFilePath, User owner, User[] operators) {
		if (port == -1 || port < 1024 || port > 65535)
			port = this.getAvailablePort();
		MinecraftServer minecraftServer = new MinecraftServer(TheBox.get(), name, jarFilePath, owner, operators);
		if (!serverList.contains(minecraftServer)) {
			logger.info("Creating Minecraft server: %s", minecraftServer.toString());
			serverList.add(minecraftServer);
			return minecraftServer;
		} else
			return getServer(name);
	}

	public boolean removeServer(MinecraftServer server) {
		if (!server.isStarted())
			return serverList.remove(server);
		else
			return false;
	}

	public MinecraftServer getServer(String name) {
		for (MinecraftServer server : serverList)
			if (server.getName().equals(name))
				return server;
		return null;
	}

	public boolean hasServers() {
		return (!serverList.isEmpty());
	}

	public List<MinecraftServer> getServers() {
		return serverList;
	}

	public void stopServers() throws ServerStopException {
		ServerStopException exception = null;
		for (MinecraftServer server : serverList) {
			try {
				server.stop();
			} catch (ServerStopException e) {
				exception = e;
			}
		}
		if (exception != null)
			throw exception;
	}

	public int getAvailablePort() {
		int port = -1;
		do {
			try {
				ServerSocket serverSocket = new ServerSocket(0);
				port = serverSocket.getLocalPort();
				serverSocket.close();
			} catch (IOException e) {
				continue;
			}
		} while (port == -1);
		return port;
	}

}