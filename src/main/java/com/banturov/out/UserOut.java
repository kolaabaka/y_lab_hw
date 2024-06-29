package com.banturov.out;

import java.nio.file.AccessDeniedException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Iterator;
import java.util.Scanner;

import com.banturov.exception.EntityNotExistException;

public class UserOut {

	public static String checkUser(String usernameDb, String passwordDb, String urlDb, Scanner scan)
			throws AccessDeniedException {
		try (Connection connection = DriverManager.getConnection(urlDb, usernameDb, passwordDb)) {
			System.out.println("Enter name");
			String name = scan.next();
			System.out.println("Enter password");
			String password = scan.next();
			PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users WHERE name = ?;");
			preparedStatement.setString(1, name);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (!resultSet.next()) {
				throw new EntityNotExistException("Not found user: " + name);
			}
			if (name.equals(resultSet.getString("name")) && password.equals(resultSet.getString("password")))
				return resultSet.getString("name");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		throw new AccessDeniedException("Access denied");
	}
}
