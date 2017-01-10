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
	
	public int getId() {
		return ordinal();
	}
	
	public static UserRank getRank(int id) {
		return UserRank.values()[id];
	}

	public static UserRank getRank(String name) {
		try {
			return UserRank.valueOf(name);
		} catch (IllegalArgumentException exception) {
			return null;
		}
	}

}