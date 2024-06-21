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
		Long timIntervalBuffer;
		
		try {
			rep.addRoom(10L);
			rep.addRoom(11L);
			rep.addRoom(12L);
		} catch (AlreadyExistException e) {
			System.out.println(e.getMessage());
		}

		try {
			rep.addEvent(1L, "March", 1L, "GooglePixel", 12L);
			rep.addEvent(2L, "March", 2L, "GayBanana", 11L);
			rep.addEvent(3L, "December", 3L, "ChikiBriki", 10L);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		while (!programFinish) {
			System.out.println("Select menu");
			menuSelect = input.next();
			switch (menuSelect) {
			case ("1"): // Show rooms
				try {
					System.out.println(rep.showRoom());
				} catch (AttributeNotFoundException e) {
					System.out.println(e.getMessage());
				}
				break;
			case ("2"):
				try {
					System.out.println(rep.showEvent());
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
				System.out.println("Enter time interval from 1 to 4");
				timIntervalBuffer = input.nextLong();
				// System.out.println("Enter author of event");
				authorEventBuf = "GooglePixel";// input.next();
				try {
					rep.addEvent(idEventBuf, dateEventBuf, timIntervalBuffer, authorEventBuf, roomNumberBuf);
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
				break;
			case ("5"):// View available slots DATE
				dateEventBuf = input.next();
				try {
					System.out.println(rep.filterDate(dateEventBuf));
				} catch (AttributeNotFoundException e) {
					System.out.println(e.getMessage());
				}
				break;
			case ("6"):// View available slots AUTHOR
				authorEventBuf = input.next();
				try {
					System.out.println(rep.filterAuthor(authorEventBuf));
				} catch (AttributeNotFoundException e) {
					System.out.println(e.getMessage());
				}
				break;
			case ("7"):// View available slots NUMBERROOM
				roomNumberBuf = input.nextLong();
				try {
					System.out.println(rep.filterNumberRoom(roomNumberBuf));
				} catch (AttributeNotFoundException e) {
					System.out.println(e.getMessage());
				}
				break;
			case ("8"):// Delete by id
				idEventBuf = input.nextLong();
				authorEventBuf = "GooglePixel";
				try {
					rep.deleteEvent(authorEventBuf, idEventBuf);
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
				break;
				
			}
		}
	}
}
