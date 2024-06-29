package com.banturov.in;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class UserIn {

	public static void addUser(String usernameDb, String passwordDb, String urlDb, Scanner scan) {
		try (Connection connection = DriverManager.getConnection(urlDb, usernameDb, passwordDb)) {
			System.out.println("Enter id");
			int id = scan.nextInt();
			System.out.println("Enter name");
			String name = scan.next();
			System.out.println("Enter password");
			String password = scan.next();
			PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO users VALUES(?,?,?);");
			preparedStatement.setInt(1, id);
			preparedStatement.setString(2, name);
			preparedStatement.setString(3, password);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
}
