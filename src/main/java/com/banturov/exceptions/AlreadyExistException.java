package com.banturov.exceptions;

/**
 * Custom exception to exclude the addition of duplicates
 */
public class AlreadyExistException extends Exception {
	public AlreadyExistException(String message) {
		super(message);
	}
}
