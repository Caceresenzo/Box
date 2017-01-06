package box.web.users;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import box.database.Databases;

public class UserManager {
	
	private static UserManager manager = new UserManager();
	
	private List<SimpleUser> users = new ArrayList<SimpleUser>();
	
	private UserManager() {
		users.clear();
	}
	
	public static UserManager getManager() {
		return manager;
	}
	
	public SimpleUser getUserByName(String name) {
		if (users.isEmpty()) return null;
		for (SimpleUser user : users) {
			if (user.getUsername().equals(name)) {
				return user;
			}
		}
		return null;
	}
	
	public Integer checkUser(String username, String password) {
		try {
			if (Databases.web.checkConnection()) {
				String sql = "SELECT id FROM `users` WHEN username=? AND password=?";
				PreparedStatement statement = Databases.data.getConnection().prepareStatement(sql);
				statement.setString(0, username);
				statement.setString(1, password);
				ResultSet result = statement.executeQuery();
				if (result.first()) {
					return result.getInt("id");
				}
			}
		} catch (SQLException exception) {
			return -1;
		}
		return -1;
	}
	
}