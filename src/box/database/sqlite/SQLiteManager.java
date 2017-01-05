package box.database.sqlite;

import java.util.ArrayList;
import java.util.List;

import box.database.Database;
import box.database.mysql.MySQL;

/**
 * 
 * @author Enzo CACERES
 */
public class SQLiteManager {
	
	private static List<Database> databases = new ArrayList<Database>();
	
	public static List<SQLite> getLite() {
		List<SQLite> list = new ArrayList<SQLite>();
		for (Database database : databases) {
			if (database instanceof SQLite) {
				list.add((SQLite) database);
			}
		}
		return list;
	}
	
	public static SQLite getLite(String name) {
		for (SQLite lite : getLite()) {
			if (lite.dbName == name) {
				return lite;
			}
		}
		return null;
	}
	
	public static List<MySQL> getMy() {
		List<MySQL> list = new ArrayList<MySQL>();
		for (Database database : databases) {
			if (database instanceof SQLite) {
				list.add((MySQL) database);
			}
		}
		return list;
	}
	
	public static MySQL getMy(String name) {
		for (MySQL my : getMy()) {
			if (my.dbName == name) {
				return my;
			}
		}
		return null;
	}
	
}