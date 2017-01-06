package box.database;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface DatabaseQueryResult {

	public void nextLine(ResultSet resultSet) throws SQLException;
	
}