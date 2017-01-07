package box.web.users;

import java.util.ArrayList;
import java.util.List;

import box.minecraft.MinecraftServer;

public class User {
	
	private UserManager manager = UserManager.getManager();
	
	private Integer id;
	private String username;
	private String password;
	private UserLevel level;
	
	private List<MinecraftServer> ownerServer;
	
	private Boolean checked = false;
	private Boolean valid = false;
	
	public User(String username, String password) {
		this.id = -1;
		this.username = username;
		this.password = password;
		this.level = UserLevel.UNKNOWN;
		
		ownerServer = new ArrayList<MinecraftServer>();
		
		check();
	}
	
	private void check() {
		if (!this.checked) this.checked = true;
		Integer id = manager.checkUser(username, password);
		if (id > -1) {
			this.id = id;
			this.valid = true;
		}
	}
	
	public Integer getId() {
		return this.id;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public UserLevel getLevel() {
		return this.level;
	}
	
	public Boolean isValid() {
		return this.valid;
	}
	
	public List<MinecraftServer> getOwnerServer() {
		return this.ownerServer;
	}
}