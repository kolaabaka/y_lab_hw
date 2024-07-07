package com.banturov.interaction;

import java.text.SimpleDateFormat;
import java.util.Scanner;

import com.banturov.entity.User;
import com.banturov.exception.EntityNotExistException;
import com.banturov.in.EventIn;
import com.banturov.in.RoomIn;
import com.banturov.out.EventOut;
import com.banturov.out.RoomOut;

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
	public static void eventPage(String usernameDb, String passwordDb, String urlDb, Scanner scan,
			SimpleDateFormat format, User user) {

		SimpleDateFormat formatter = format;

		Scanner input = scan;
		boolean programFinish = false;
		String menuSelect;

		while (!programFinish) {
			System.out.println("Select menu\n1 - Show all rooms\n2 - Show all events\n3 - Add new room"
					+ "\n4 - Add new event\n5 - View available slots DATE\n6 - View available slots AUTHOR"
					+ "\n7 - View available slots NUMBER ROOM\n8 - Update envet" + "\n9 - Delete event\n10 - Exit");
			menuSelect = input.next();
			switch (menuSelect) {
			case ("1"): // Show rooms
				RoomOut.showRoom(usernameDb, passwordDb, urlDb, scan);
				break;
			case ("2"): // Show events
				try {
					EventOut.showEvent(usernameDb, passwordDb, urlDb, scan);
				} catch (EntityNotExistException e) {
					System.out.println(e.getMessage());
				}
				break;
			case ("3"): // Add room
				RoomIn.addRoom(usernameDb, passwordDb, urlDb, scan);
				break;
			case ("4"): // Add event
				EventIn.addEvent(user.getName(), usernameDb, passwordDb, urlDb, scan);
				break;
			case ("5"):// View available slots DATE
				try {
					EventOut.showEventDate(usernameDb, passwordDb, urlDb, scan);
				} catch (EntityNotExistException e) {
					System.out.println(e.getMessage());
				}
				break;
			case ("6"):// View available slots AUTHOR
				try {
					EventOut.showEventAuthor(usernameDb, passwordDb, urlDb, scan);
				} catch (EntityNotExistException e) {
					System.out.println(e.getMessage());
				}
				break;
			case ("7"):// View available slots NUMBERROOM
				try {
					EventOut.showEventNumberRoom(usernameDb, passwordDb, urlDb, scan);
				} catch (EntityNotExistException e) {
					System.out.println(e.getMessage());
				}
				break;
			case ("8"):// Update EVENT

				break;
			case ("9"):// Delete by id
				EventIn.deleteEvent(usernameDb, usernameDb, passwordDb, urlDb, scan);
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
