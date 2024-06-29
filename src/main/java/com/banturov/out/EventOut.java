package com.banturov.out;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import com.banturov.entity.Event;
import com.banturov.exception.EntityNotExistException;

public class EventOut {

	public static void showEvent(String usernameDb, String passwordDb, String urlDb, Scanner scan) throws EntityNotExistException {
		try (Connection connection = DriverManager.getConnection(urlDb, usernameDb, passwordDb)) {
			PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM events;");
			ResultSet resultSet = preparedStatement.executeQuery();
			Event event = new Event();
//			if(!resultSet.next()) {
//				throw new EntityNotExistException("No one events exist");
//			}
			while (resultSet.next()) {
				event.setId(resultSet.getLong("id"));
				event.setDate(resultSet.getString("date"));
				event.setNumberRoom(resultSet.getLong("room"));
				event.setTimeInterval(resultSet.getLong("timeinterval"));
				event.setAuthor(resultSet.getString("author"));
				System.out.println(event);
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static void showEventAuthor(String usernameDb, String passwordDb, String urlDb, Scanner scan) throws EntityNotExistException {
		try (Connection connection = DriverManager.getConnection(urlDb, usernameDb, passwordDb)) {
			System.out.println("Enter author name:");
			String authorName = scan.next();
			PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM events WHERE author = ?;");
			preparedStatement.setString(1, authorName);
			ResultSet resultSet = preparedStatement.executeQuery();
//			if(!resultSet.next()) {
//				throw new EntityNotExistException("No one events exist author " + authorName);
//			}
			Event event = new Event();
			while (resultSet.next()) {
				event.setId(resultSet.getLong("id"));
				event.setDate(resultSet.getString("date"));
				event.setNumberRoom(resultSet.getLong("room"));
				event.setTimeInterval(resultSet.getLong("timeinterval"));
				event.setAuthor(resultSet.getString("author"));
				System.out.println(event);
			}
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static void showEventNumberRoom(String usernameDb, String passwordDb, String urlDb, Scanner scan) throws EntityNotExistException {
		try (Connection connection = DriverManager.getConnection(urlDb, usernameDb, passwordDb)) {
			System.out.println("Enter number room:");
			String numberRoom = scan.next();
			PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM events WHERE room = ?;");
			preparedStatement.setString(1, numberRoom);
			ResultSet resultSet = preparedStatement.executeQuery();
//			if(!resultSet.next()) {
//				throw new EntityNotExistException("No one events exist authro " + numberRoom);
//			}
			Event event = new Event();
			while (resultSet.next()) {
				event.setId(resultSet.getLong("id"));
				event.setDate(resultSet.getString("date"));
				event.setNumberRoom(resultSet.getLong("room"));
				event.setTimeInterval(resultSet.getLong("timeinterval"));
				event.setAuthor(resultSet.getString("author"));
				System.out.println(event);
			}
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static void showEventDate(String usernameDb, String passwordDb, String urlDb, Scanner scan) throws EntityNotExistException {
		try (Connection connection = DriverManager.getConnection(urlDb, usernameDb, passwordDb)) {
			System.out.println("Enter date:");
			String date = scan.next();
			PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM events WHERE date = ?;");
			preparedStatement.setString(1, date);
			ResultSet resultSet = preparedStatement.executeQuery();
//			if(!resultSet.next()) {
//				throw new EntityNotExistException("No one events exist authro " + date);
//			}
			Event event = new Event();
			while (resultSet.next()) {
				event.setId(resultSet.getLong("id"));
				event.setDate(resultSet.getString("date"));
				event.setNumberRoom(resultSet.getLong("room"));
				event.setTimeInterval(resultSet.getLong("timeinterval"));
				event.setAuthor(resultSet.getString("author"));
				System.out.println(event);
			}
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
}
