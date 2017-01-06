package box;

import java.sql.SQLException;

import box.database.Database;

public class Test {
	
	public static void main(String[] args) {

		Database.setupDefault();
		
		try {
			System.out.println(Database.web.checkConnection());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}