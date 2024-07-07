package com.banturov.in;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class EventIn {

	public static void addEvent(String name, String usernameDb, String passwordDb, String urlDb, Scanner scan) {
		try (Connection connection = DriverManager.getConnection(urlDb, usernameDb, passwordDb)) {
			System.out.println("Enter id");
			Long id = scan.nextLong();
			System.out.println("Enter room number");
			Long roomNumber = scan.nextLong();
			System.out.println("Enter timeinterval 1 to 4");
			int timeinterval = scan.nextInt();
			System.out.println("Enter date");
			String date = scan.next();
			String author = name;
			PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO events VALUES(?,?,?,?,?);");
			preparedStatement.setLong(1, id);
			preparedStatement.setLong(2, roomNumber);
			preparedStatement.setInt(3, timeinterval);
			preparedStatement.setString(4, date);
			preparedStatement.setString(5, author);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static void deleteEvent(String name, String usernameDb, String passwordDb, String urlDb, Scanner scan) {
		try (Connection connection = DriverManager.getConnection(urlDb, usernameDb, passwordDb)) {
			System.out.println("Enter id");
			Long id = scan.nextLong();
			PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM events WHERE id = ?;");
			preparedStatement.setLong(1, id);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
}
