package com.banturov.interaction;

import java.nio.file.AccessDeniedException;
import java.util.Scanner;

import com.banturov.entity.User;
import com.banturov.in.UserIn;
import com.banturov.out.UserOut;

/**
 * Registers users and performs authentication
 */
public class LoginLayer {
	/**
	 * @param scan - take Scanner instance for input data
	 * @return Returns the user to pass it to the next method and exclude
	 *         unauthorized access
	 * @throws AccessDeniedException 
	 */
	public static User eventPage(String usernameDb, String passwordDb, String urlDb,Scanner scan){
		Scanner input = scan;
		boolean programFinish = false;

		String namebuf = null;
		
		String menuSelect;

		while (!programFinish) {
			System.out.println("1-login\n2-registration");
			menuSelect = input.next();
			switch (menuSelect) {
			case ("1"):
				try {
					namebuf = UserOut.checkUser(usernameDb, passwordDb, urlDb, scan);
					programFinish = true;
				} catch (AccessDeniedException e) {
					System.out.println(e.getMessage());
				}
				break;
			case ("2"):
				UserIn.addUser(usernameDb, passwordDb, urlDb, scan);				
				break;
			default:
				System.out.println("Unsupported option");
			}
		}
		return new User(namebuf);
	}
}
