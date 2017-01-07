package box.web.user;

public enum UserRank {

	ADMIN(new Permission[] {
			Permission.SERVER_CREATE,
			Permission.SERVER_REMOVE,
			Permission.SERVER_START,
			Permission.SERVER_STOP
		}
	),
	OPERATOR(new Permission[] {
			Permission.SERVER_START,
			Permission.SERVER_STOP
		}
	),
	DEFAULT(new Permission[0]);

	private final Permission[] permissions;

	private UserRank(Permission[] permissions) {
		this.permissions = permissions;
	}

	public Permission[] getPermissions() {
		return permissions;
	}

	public static UserRank getByName(String name) {
		for (UserRank level : UserRank.values()) {
			if (level.toString().equalsIgnoreCase(name)) {
				return level;
			}
		}
		return null;
	}

}