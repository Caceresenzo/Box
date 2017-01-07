package box.web.user;

public enum Permission {

	SERVER_CREATE, SERVER_START, SERVER_STOP, SERVER_REMOVE;

	public String getPermissionKey() {
		return name().toLowerCase().replace("_", ".");
	}

	public static Permission getByKey(String permissionKey) {
		try {
			return Permission.valueOf(permissionKey.toUpperCase().replace(".", "_"));
		} catch (IllegalArgumentException exception) {
			return null;
		}
	}

	public static Permission[] parsePermissions(String rawPermissions) {
		String[] splitPermissions = rawPermissions.split(";");
		Permission[] permissions = new Permission[splitPermissions.length];
		for (int i = 0; i < splitPermissions.length; i++)
			permissions[i] = Permission.getByKey(splitPermissions[i]);
		return permissions;
	}

}