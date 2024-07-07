package com.banturov.in;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class RoomIn {

	public static void addRoom(String usernameDb, String passwordDb, String urlDb, Scanner scan) {
		try (Connection connection = DriverManager.getConnection(urlDb, usernameDb, passwordDb)) {
			System.out.println("Enter id");
			int id = scan.nextInt();
			System.out.println("Enter room number");
			int roomNumber = scan.nextInt();
			PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO rooms VALUES(?,?);");
			preparedStatement.setInt(1, id);
			preparedStatement.setInt(2, roomNumber);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

}
