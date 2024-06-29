package com.banturov.out;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import com.banturov.entity.Room;
import com.banturov.exception.EntityNotExistException;

public class RoomOut {

	public static void showRoom(String usernameDb, String passwordDb, String urlDb, Scanner scan) {
		try (Connection connection = DriverManager.getConnection(urlDb, usernameDb, passwordDb)) {
			PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM rooms;");
			ResultSet resultSet = preparedStatement.executeQuery();
			Room room = new Room();
			while (resultSet.next()) {
				room.setId(resultSet.getLong("id"));
				room.setId(resultSet.getLong("room"));
				System.out.println(room);
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void showOneRoom(int id, String usernameDb, String passwordDb, String urlDb, Scanner scan) throws EntityNotExistException {
		try (Connection connection = DriverManager.getConnection(urlDb, usernameDb, passwordDb)) {
			PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM rooms WHERE ?;");
			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			if(!resultSet.next()) {
				throw new EntityNotExistException("Not exist " + id + " record");
			}
			while (resultSet.next()) {
				Room room = new Room();
				room.setId(resultSet.getLong("id"));
				room.setId(resultSet.getLong("room"));
				System.out.println(room);
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
}
