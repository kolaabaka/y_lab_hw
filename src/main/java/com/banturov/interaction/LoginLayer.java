package com.banturov.interaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.banturov.events.User;

/**
 * Registers users and performs authentication
 */
public class LoginLayer {
	/**
	 * @param scan - take Scanner instance for input data
	 * @return Returns the user to pass it to the next method and exclude
	 *         unauthorized access
	 */
	public static User eventPage(Scanner scan) {
		Scanner input = scan;
		boolean programFinish = false;

		List<User> userList = new ArrayList<>();

		String menuSelect;

		String nameBuf = null;
		String passwordBuf = null;

		while (!programFinish) {
			System.out.println("1-login\n2-registration");
			menuSelect = input.next();
			switch (menuSelect) {
			case ("1"):
				System.out.println("Input name");
				nameBuf = input.next();
				System.out.println("Input password");
				passwordBuf = input.next();
				if (userList.contains(new User(nameBuf, passwordBuf))) {
					programFinish = true;
				} else {
					System.out.println("There is no such user");
				}
				break;
			case ("2"):
				System.out.println("Input name");
				nameBuf = input.next();
				System.out.println("Input password");
				passwordBuf = input.next();
				userList.add(new User(nameBuf, passwordBuf));
				break;
			default:
				System.out.println("Unsupported option");
			}
		}
		return new User(nameBuf, passwordBuf);
	}
}
