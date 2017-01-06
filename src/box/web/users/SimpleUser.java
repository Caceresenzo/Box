package box.web.users;

public class SimpleUser {
	
	private UserManager manager = UserManager.getManager();
	
	private Integer id;
	private String username;
	private String password;
	private UserLevel level;
	
	private Boolean checked = false;
	private Boolean valid = false;
	
	public SimpleUser(String username, String password) {
		this.username = username;
		this.password = password;
		
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
	
	public Boolean isValid() {
		return this.valid;
	}
}