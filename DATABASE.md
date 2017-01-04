# Database Tutorial

------------------------------------------------------------

#### Init:

	MySQL mysql = new MySQL("hostname", "port (def: 3606)", "database", "username", "password");
	Connection connection = null;

And open it!

	connection = mysql.openConnection();

#### Getting values

	Statement statement = connection.createStatement();
	
	ResultSet result = statement.executeQuery("SELECT * FROM tokens WHERE key = '" + value + "';");
	result.next();
	
	if(result.getString("key") == null) {
		tokens = 0;
	} else {
		tokens = result.getInt("tokens");
	}

#### Setting information

###### Direct SQL

	statement.executeUpdate("INSERT INTO tokens (`key`, `tokens`) VALUES ('" + value + "', '0');");
	System.out.println("Inserted info");

###### Prepared Statements

	PreparedStatement statement = connection.prepareStatement("INSERT INTO `table`(a, b, c) VALUES (?, ?, ?);");
	statement.setString(1, "String of 'a' value");
	statement.setString(2, "String of 'b' value");
	statement.setString(3, "String of 'c' value");
	statement.executeUpdate();

#### Bug

If you have a NullPointerException when calling:

	Statement statement = connection.createStatement();

Try this:

	Statement statement = mysql.open().createStatement();

## End, Enjoy!