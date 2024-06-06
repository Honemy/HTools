package com.honemy.ht.exception;

import com.honemy.ht.debug.ModernDebug;
import lombok.Getter;
import lombok.Setter;

/**
 * Custom exception class for the Ht application.
 * This class extends RuntimeException and provides additional functionality
 * such as automatic error saving.
 */
public class HtException extends RuntimeException {

	/**
	 * A static flag to control whether errors should be saved automatically.
	 */
	@Getter
	@Setter
	private static boolean saveErrorAutomatically = true;

	/**
	 * Constructs a new HtException with the specified cause.
	 * If saveErrorAutomatically is true, the error is saved.
	 *
	 * @param t the cause of the exception.
	 */
	public HtException(Throwable t) {
		super(t);

		if (saveErrorAutomatically)
			ModernDebug.saveError(t);
	}

	/**
	 * Constructs a new HtException with the specified detail message.
	 * If saveErrorAutomatically is true, the error is saved.
	 *
	 * @param message the detail message.
	 */
	public HtException(String message) {
		super(message);

		if (saveErrorAutomatically)
			ModernDebug.saveError(this, message);
	}

	/**
	 * Constructs a new HtException with the specified cause and detail message.
	 * If saveErrorAutomatically is true, the error is saved.
	 *
	 * @param t       the cause of the exception.
	 * @param message the detail message.
	 */
	public HtException(Throwable t, String message) {
		super(message, t);

		if (saveErrorAutomatically)
			ModernDebug.saveError(t, message);
	}

	/**
	 * Constructs a new HtException.
	 * If saveErrorAutomatically is true, the error is saved.
	 */
	public HtException() {
		if (saveErrorAutomatically)
			ModernDebug.saveError(this);
	}

	/**
	 * Returns the detail message string of this HtException.
	 *
	 * @return the detail message string of this HtException instance.
	 */
	@Override
	public String getMessage() {
		return "Report: " + super.getMessage();
	}
}