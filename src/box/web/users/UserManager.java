package box.web.users;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import box.database.DatabaseManager;

public class UserManager {
	
	private static UserManager manager = new UserManager();
	
	private List<User> users = new ArrayList<User>();
	
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
	
}