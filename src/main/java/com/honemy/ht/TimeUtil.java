package com.honemy.ht;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Utility class for time-related operations.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TimeUtil {

	// Define the date format as a constant
	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

	/**
	 * Returns the current date and time formatted as "dd-MM-yyyy HH:mm:ss".
	 *
	 * @return A string representing the current date and time.
	 */
	public static String getFormattedDate() {
		return LocalDateTime.now().format(DATE_FORMATTER);
	}

}