package com.banturov.main;

import java.util.Locale;
import java.util.Scanner;
import java.text.SimpleDateFormat;

import com.banturov.events.User;
import com.banturov.interaction.EventLayer;
import com.banturov.interaction.LoginLayer;

/**
 * Main class for launching the application, uses statics methods, Passes an
 * instance of date formatter and Scanner instance
 */
public class Main {

	public static void main(String[] args) {
		SimpleDateFormat format = new SimpleDateFormat("dd-mm-yyyy", Locale.ENGLISH);
		Scanner inputScan = new Scanner(System.in);
		User user = LoginLayer.eventPage(inputScan);
		EventLayer.eventPage(inputScan, format, user);
	}

}
