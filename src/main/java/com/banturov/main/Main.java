package com.banturov.main;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Scanner;

import com.banturov.entity.User;
import com.banturov.interaction.EventLayer;
import com.banturov.interaction.LoginLayer;
import com.banturov.liquibase.LiquibasePrepare;

/**
 * Main class for launching the application, uses statics methods, Passes an
 * instance of date formatter and Scanner instance
 */
public class Main {

	private static final String URL = "jdbc:mysql://localhost:3306/event-room";
	private static final String USER_NAME = "root";
	private static final String PASSWORD = "root";

	public static void main(String[] args) {
		Scanner inputScan = new Scanner(System.in);
		LiquibasePrepare.prepare(USER_NAME, PASSWORD, URL);
		SimpleDateFormat format = new SimpleDateFormat("dd-mm-yyyy", Locale.ENGLISH);
		User user = LoginLayer.eventPage(USER_NAME, PASSWORD, URL, inputScan);
		EventLayer.eventPage(USER_NAME, PASSWORD, URL, inputScan, format, user);
		inputScan.close();
	}

}
