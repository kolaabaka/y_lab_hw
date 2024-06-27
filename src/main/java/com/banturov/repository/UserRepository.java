package com.banturov.repository;

import java.util.HashSet;
import java.util.Set;

import com.banturov.entity.User;
import com.banturov.exceptions.AlreadyExistException;

public class UserRepository {

	Set<User> userList = new HashSet<>();

	public void addUser(String userName, String password) throws AlreadyExistException {
		for (User user : userList) {
			if (user.getName() == userName) {
				throw new AlreadyExistException("Username " + userName + " already used");
			}
		}
		if (userName.length() == 0 || password.length() == 0) {
			throw new IllegalArgumentException("Username and password cannot be NULL");
		}
		userList.add(new User(userName, password));
	}

	public boolean checkUserExist(String userName, String password) {
		for (User user : userList) {
			if (user.getName().equals(password) && user.getPassword().equals(password)) {
				return true;
			}
		}
		return false;
	}
}
