package box.web.user;

import java.util.ArrayList;
import java.util.List;

import box.TheBox;
import box.minecraft.MinecraftServer;

public class User {
	
	private final TheBox theBox = TheBox.getInstance();
	private final int id;
	private String username;
	private String password;
	private UserRank rank;
	private UserPermission permission;
	private final List<MinecraftServer> ownedServer = new ArrayList<MinecraftServer>();
	
	protected User(int id, String username, String password, UserRank rank, Permission[] permissions) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.rank = rank;
		this.permission = new UserPermission(this);
		this.permission.loadFromArray(permissions);
		this.permission.loadDefaultPermissions();
		
		for(MinecraftServer minecraftServer : theBox.getServerManager().getServers())
			if(minecraftServer.getOwner().equals(this))
				this.ownedServer.add(minecraftServer);
	}
	
	public int getId() {
		return id;
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public UserRank getRank() {
		return rank;
	}
	
	public List<MinecraftServer> getOwnedServer() {
		return ownedServer;
	}
}