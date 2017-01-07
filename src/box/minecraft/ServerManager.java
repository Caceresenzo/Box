package box.minecraft;

import java.util.ArrayList;
import java.util.List;

import box.TheBox;
import box.minecraft.exception.ServerStopException;

public class ServerManager {
	
	public static ServerManager manager = new ServerManager();
	
	public List<MinecraftServer> servers = new ArrayList<MinecraftServer>();
	
	public ServerManager() {
		if (checkList()) {
			for (MinecraftServer server : servers) {
				try {
					if (server.isStarted()) {
						server.sendMessage("tellraw @a [\"\",{\"text\":\"[Box] \",\"color\":\"blue\",\"bold\":true,\"clickEvent\":{\"action\":\"open_url\",\"value\":\"\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"The Box™\"}]}}},{\"text\":\"Box's ServerManager instance recreating.... Stopping Server...\",\"color\":\"none\",\"bold\":false}]");
						server.sendCommand("save all");
						server.stop();
					}
				} catch (ServerStopException exception) {
					exception.printStackTrace();
				}
			}
		}
		servers = new ArrayList<MinecraftServer>();
	}
	
	public static ServerManager getManager() {
		return manager;
	}
	
	public MinecraftServer createServer(String name, String jarFile, String owner, String[] subowner) {
		MinecraftServer minecraftServer = new MinecraftServer(TheBox.get(), name, jarFile, owner, subowner);
		if (checkList()) {
			boolean alreadyNamed = false;
			for (MinecraftServer server : servers) {
				if (server.getName() == name && alreadyNamed == false) {
					alreadyNamed = true;
					return server;
				}
			}
		}
		servers.add(minecraftServer);
		return minecraftServer;
	}
	
	public MinecraftServer getServer(String name) {
		if (checkList()) {
			for (MinecraftServer server : servers) {
				if (server.getName().equals(name)) {
					return server;
				}
			}
		}
		return null;
	}
	
	public boolean addServer(MinecraftServer server) {
		if (checkList() && !servers.contains(server)) {
			return servers.add(server);
		}
		return false;
	}
	
	public boolean removeServer(MinecraftServer server) {
		if (checkList() && servers.contains(server) && server.isStarted()) {
			return servers.remove(server);
		}
		return false;
	}
	
	public List<MinecraftServer> getServers() {
		return this.servers;
	}
	
	public void stopServers() throws ServerStopException {
		if (checkList()) {
			for (MinecraftServer server : servers) {
				server.stop();
			}
		}
	}
	
	private boolean checkList() {
		if (servers == null) {
			servers = new ArrayList<MinecraftServer>();
		}
		return !servers.isEmpty();
	}
	
}