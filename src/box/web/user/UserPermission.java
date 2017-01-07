package box.web.user;

import java.util.ArrayList;
import java.util.List;

public class UserPermission {

	private final User user;
	private final List<Permission> permissionList = new ArrayList<Permission>();

	protected UserPermission(User user) {
		this.user = user;
	}

	public boolean hasPermission(Permission permission) {
		return permissionList.contains(permission);
	}

	public boolean addPermission(Permission permission) {
		return permissionList.add(permission);
	}

	public boolean removePermission(Permission permission) {
		return permissionList.remove(permission);
	}
	
	public void loadDefaultPermissions() {
		permissionList.clear();
		for(Permission permission : user.getRank().getPermissions())
			permissionList.add(permission);
	}
	
	public void loadFromArray(Permission[] permissions) {
		for(Permission permission : permissions)
			permissionList.add(permission);
	}

	public User getUser() {
		return user;
	}

}