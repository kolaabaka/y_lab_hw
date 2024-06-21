package com.banturov.interaction;

import java.util.Scanner;

import javax.management.AttributeNotFoundException;

import com.banturov.events.Repository;
import com.banturov.exceptions.AlreadyExistException;

public class EventLayer {

	public static void eventPage(Scanner scan) {

		Scanner input = scan;
		Repository rep = new Repository();
		boolean programFinish = false;
		String menuSelect;

		Long roomNumberBuf;

		Long idEventBuf;
		String dateEventBuf;
		String authorEventBuf;
		while (!programFinish) {
			System.out.println("Select menu");
			menuSelect = input.next();
			switch (menuSelect) {
			case ("1"): // Show rooms
				try {
					rep.showRoom();
				} catch (AttributeNotFoundException e) {
					System.out.println(e.getMessage());
				}
				break;
			case ("2"):
				try {
					rep.showEvent();
				} catch (AttributeNotFoundException e) {
					System.out.println(e.getMessage());
				}
				break;
			case ("3"): // Add room
				System.out.println("Enter number of room");
				roomNumberBuf = input.nextLong();
				try {
					rep.addRoom(roomNumberBuf);
				} catch (AlreadyExistException e) {
					System.out.println(e.getMessage());
				}
				break;
			case ("4"): // Add event
				System.out.println("Enter number of room");
				roomNumberBuf = input.nextLong();
				System.out.println("Enter event id");
				idEventBuf = input.nextLong();
				System.out.println("Enter date of event");
				dateEventBuf = input.next();
				//System.out.println("Enter author of event");
				authorEventBuf = "Google pixel";// input.next();
				try {
					rep.addEvent(idEventBuf,dateEventBuf,authorEventBuf,roomNumberBuf);
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
				break;
			}
		}
	}
}
