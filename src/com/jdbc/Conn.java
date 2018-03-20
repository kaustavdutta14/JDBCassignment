package com.jdbc;

import java.sql.DriverManager;
import java.sql.SQLException;

public class Conn{
	
	public static java.sql.Connection startConnection() {
		try {
		Class.forName("com.mysql.jdbc.Driver");
	} catch (ClassNotFoundException c) {
	}
	java.sql.Connection connection = null;

	try {
		connection = DriverManager
		.getConnection("jdbc:mysql://localhost:3306/demo","root", "1234");

	} 
	catch (SQLException e) {
		return null;
	}
	return connection;
	}
}
