package box;

import box.database.DatabaseManager;
import box.utils.RandomUtils;

public class Test {
	
	private static DatabaseManager dbmanager = DatabaseManager.getManager();
	
	public static void main(String[] args) {
		System.out.println(RandomUtils.getRandomString(100));
		dbmanager.setupDatabase();
		dbmanager.initDatabase();
	}
	
}