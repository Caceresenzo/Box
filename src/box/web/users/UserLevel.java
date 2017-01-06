package box.web.users;

public enum UserLevel {
	
	ADMIN, USER, UNKNOWN;
	
	public static UserLevel getByName(String name) {
		for (UserLevel level : UserLevel.values()) {
			if (level.toString().equalsIgnoreCase(name)) {
				return level;
			}
		}
		return UserLevel.UNKNOWN;
	}
}