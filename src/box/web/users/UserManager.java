package box.web.users;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import box.database.Database;
import box.database.DatabaseManager;
import net.sociuris.logger.Logger;

public class UserManager {
	
	private static final Logger logger = Logger.getLogger();
	
	private static UserManager manager = new UserManager();
	
	private List<User> users = new ArrayList<User>();
	
	private Boolean registered = false;
	
	private UserManager() {
		users.clear();
	}
	
	public static UserManager getManager() {
		return manager;
	}
	
	public User getUserByName(String name) {
		if (users.isEmpty()) return null;
		for (User user : users) {
			if (user.getUsername().equals(name)) {
				return user;
			}
		}
		return null;
	}
	
	public Integer checkUser(String username, String password) {
		try {
			if (DatabaseManager.DATA.isConnected()) {
				String sql = "SELECT id FROM `users` WHERE `username`='" + username + "' AND `password`='" + password + "'";
				Statement statement = DatabaseManager.DATA.getConnection().createStatement();
				ResultSet result = statement.executeQuery(sql);
				if (result.next()) {
					return result.getInt("id");
				}
			}
		} catch (SQLException exception) {
			exception.printStackTrace();
		}
		return -1;
	}
	
	public boolean addUser(User user) {
		if (user != null && !users.contains(user)) {
			logger.debug("Registered User : " + user.getUsername());
			return users.add(user);
		}
		return false;
	}
	
}