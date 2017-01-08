package box.web.user;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import box.database.Database;
import box.database.DatabaseManager;
import net.sociuris.logger.Logger;

public class UserManager {

	private static final String USER_SQL_REQUEST = "SELECT id FROM `users` WHERE `username`='%s'";

	private final Logger logger = Logger.getLogger();
	private final Map<String, User> userMap = new HashMap<String, User>();

	public UserManager() {
	}

	public User getUser(String username) {
		User user = userMap.get(username);
		if (user != null)
			return user;
		else {
			Database database = DatabaseManager.WEB;
			try {
				if (database.isConnected()) {
					ResultSet result = database.executeQuery(String.format(UserManager.USER_SQL_REQUEST, username));
					if (result.next()) {
						user = new User(
								result.getInt("id"),
								result.getString("username"),
								result.getString("password"),
								UserRank.getRank(result.getString("rank")),
								Permission.parsePermissions(result.getString("permissions"))
							);
						userMap.put(user.getUsername(), user);
						return user;
					} else
						return null;
				} else
					return null;
			} catch (SQLException e) {
				logger.printStackTrace(e);
				return null;
			}
		}
	}

}