package com.banturov.tests;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

@DisplayName("EventRepository test")
@TestInstance(Lifecycle.PER_CLASS)
class SqlTestContainer {

//	static final PostgreSQLContainer postgres = new PostgreSQLContainer<>(DockerImageName.parse("postgres:16-alpine"))
//			.withDatabaseName("event-room").withUsername("AdminName").withPassword("passwordTEST")
//			.withExposedPorts(5432);

	@Test
	void testMySQLContainerIsRunning() throws SQLException {
		PostgreSQLContainer postgres = new PostgreSQLContainer<>(DockerImageName.parse("postgres:16-alpine"))
				.withDatabaseName("event-room").withUsername("AdminName").withPassword("passwordTEST")
				.withExposedPorts(5432);
		postgres.start();
		Connection connection = null;
		System.out.println(postgres.isRunning());
		try {
			connection = DriverManager.getConnection("jdbc:tc:postgresql://localhost:5432/event-room", "AdminName",
					"passwordTEST");
			System.out.println("Driver manager connected to container");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		assertThat(postgres.isRunning()).isTrue();
		assertThat(connection.isClosed()).isFalse();
		connection.close();
		postgres.close();
	}
}
