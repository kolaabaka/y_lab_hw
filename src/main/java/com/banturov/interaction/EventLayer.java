package com.banturov.interaction;

import java.text.SimpleDateFormat;
import java.util.Scanner;

import com.banturov.entity.User;
import com.banturov.exceptions.AlreadyExistException;
import com.banturov.exceptions.EntityNotExistException;
import com.banturov.repository.EventRepository;

/**
 * Class for providing a user interface
 */
public class EventLayer {

	/**
	 * 
	 * @param scan   - take Scanner instance for input data
	 * @param format - checks if the date is entered correctly
	 * @param user   - authenticated user
	 */
	public static void eventPage(Scanner scan, SimpleDateFormat format, User user) {

		SimpleDateFormat formatter = format;

		Scanner input = scan;
		EventRepository rep = new EventRepository();
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
			rep.addEvent(1L, "04-04-2004", 1L, "GooglePixel", 12L, formatter);
			rep.addEvent(2L, "01-01-2001", 2L, "HonorA900", 11L, formatter);
			rep.addEvent(3L, "09-12-2024", 2L, "BukaTuka", 10L, formatter);
			rep.addEvent(4L, "09-12-2024", 3L, "BukaTuka", 10L, formatter);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		while (!programFinish) {
			System.out.println("Select menu\n1 - Show all rooms\n2 - Show all events\n3 - Add new room"
					+ "\n4 - Add new event\n5 - View available slots DATE\n6 - View available slots AUTHOR"
					+ "\n7 - View available slots NUMBER ROOM\n8 - Update envet" + "\n9 - Delete event\n10 - Exit");
			menuSelect = input.next();
			switch (menuSelect) {
			case ("1"): // Show rooms
				try {
					System.out.println(rep.showRoom());
				} catch (EntityNotExistException e) {
					System.out.println(e.getMessage());
				}
				break;
			case ("2"): // Show events
				try {
					System.out.println(rep.showEvent());
				} catch (EntityNotExistException e) {
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
				System.out.println("Enter time interval from 1 to 4(9:00-11:00,13:00-15:00,15:00-17:00,17:00-19:00)");
				timIntervalBuffer = input.nextLong();
				authorEventBuf = user.getName();
				try {
					rep.addEvent(idEventBuf, dateEventBuf, timIntervalBuffer, authorEventBuf, roomNumberBuf, formatter);
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
				break;
			case ("5"):// View available slots DATE
				System.out.println("Enter date (dd-mm-yyyy)");
				dateEventBuf = input.next();
				try {
					System.out.println(rep.filterDate(dateEventBuf));
				} catch (EntityNotExistException e) {
					System.out.println(e.getMessage());
				}
				break;
			case ("6"):// View available slots AUTHOR
				System.out.println("Enter author name");
				authorEventBuf = input.next();
				try {
					System.out.println(rep.filterAuthor(authorEventBuf));
				} catch (EntityNotExistException e) {
					System.out.println(e.getMessage());
				}
				break;
			case ("7"):// View available slots NUMBERROOM
				System.out.println("Enter neumber of hall");
				roomNumberBuf = input.nextLong();
				try {
					System.out.println(rep.filterNumberRoom(roomNumberBuf));
				} catch (EntityNotExistException e) {
					System.out.println(e.getMessage());
				}
				break;
			case ("8"):// Update EVENT
				System.out.println("Enter event id");
				idEventBuf = input.nextLong();
				System.out.println("Enter number of room");
				roomNumberBuf = input.nextLong();
				System.out.println("Enter date of event");
				dateEventBuf = input.next();
				System.out.println("Enter time interval from 1 to 4(9:00-11:00,13:00-15:00,15:00-17:00,17:00-19:00)");
				timIntervalBuffer = input.nextLong();
				authorEventBuf = user.getName();// input.next();
				try {
					rep.updateEvent(idEventBuf, dateEventBuf, timIntervalBuffer, authorEventBuf, roomNumberBuf,
							formatter);
				} catch (EntityNotExistException e) {
					System.out.println(e.getMessage());
				}
				break;
			case ("9"):// Delete by id
				System.out.println("Enter event ID");
				idEventBuf = input.nextLong();
				authorEventBuf = user.getName();
				try {
					System.out.println("Delete event - " + rep.deleteEvent(authorEventBuf, idEventBuf));
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
				break;
			case ("10"):
				System.out.println("Program finished");
				programFinish = true;
				break;
			default:
				System.out.println("Unsupported option");

			}
		}
	}
}
