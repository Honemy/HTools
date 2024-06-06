package com.honemy.ht.exception;

import lombok.Getter;

/**
 * Custom exception class for command-related errors.
 * This class extends RuntimeException and provides a way to store multiple error messages.
 */
@Getter
public class CommandException extends RuntimeException {

	/**
	 * An array of error messages associated with the exception.
	 */
	private final String[] messages;

	/**
	 * Constructor for the CommandException.
	 *
	 * @param messages the error messages associated with the exception.
	 */
	public CommandException(String... messages) {
		super("");

		this.messages = messages;
	}
}