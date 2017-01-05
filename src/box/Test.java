package box;

import java.sql.Connection;
import java.sql.SQLException;

import box.database.mysql.MySQL;

public class Test {
	
	public static void main(String[] args) {
		MySQL mysql = new MySQL("localhost", "3306", "root", "");
		Connection connection = null;
		
		try {
			connection = mysql.openConnection();

			System.out.println(String.valueOf(connection.getMetaData()));
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
	}
	
}