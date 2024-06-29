package com.banturov.liquibase;

import java.sql.Connection;
import java.sql.DriverManager;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;

/**
 * 
 */
public class LiquibasePrepare {

	public static void prepare(String usernameDb, String passwordDb, String urlDb) {
		try {
			Connection connection = DriverManager.getConnection(urlDb, usernameDb, passwordDb);
			Database correctDataBase = DatabaseFactory.getInstance()
					.findCorrectDatabaseImplementation(new JdbcConnection(connection));
			Liquibase liquibase = new Liquibase("db/changelog/changelog.xml", new ClassLoaderResourceAccessor(),
					correctDataBase);
			liquibase.update();
			System.out.println("Liquibase finish");
		} catch (Exception e) {
			System.out.println("SQL exception in migration " + e.getMessage());
		}
	}
}
