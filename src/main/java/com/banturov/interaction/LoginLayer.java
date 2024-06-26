package com.banturov.interaction;

import java.util.Scanner;

import com.banturov.entity.User;
import com.banturov.repository.UserRepository;

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

		UserRepository rep = new UserRepository();

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
				if (rep.checkUserExist(nameBuf, passwordBuf)) {
					programFinish = true;
				}
				break;
			case ("2"):
				System.out.println("Input name");
				nameBuf = input.next();
				System.out.println("Input password");
				passwordBuf = input.next();
				try {
					rep.addUser(nameBuf, passwordBuf);
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
				break;
			default:
				System.out.println("Unsupported option");
			}
		}
		return new User(nameBuf, passwordBuf);
	}
}
