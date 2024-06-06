package com.honemy.ht;

import com.honemy.ht.debug.ModernDebug;
import com.honemy.ht.exception.HtException;
import com.honemy.ht.logger.ModernLogLevel;
import com.honemy.ht.logger.ModernLogger;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bukkit.ChatColor;

import java.util.*;

/**
 * Utility class for common operations.
 */
@SuppressWarnings("unused")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Common {

	/**
	 * Returns the given value if it is not null, otherwise returns the default value.
	 *
	 * @param value        The value to check for nullity.
	 * @param defaultValue The default value to return if the value is null.
	 * @return The value if it is not null, otherwise the default value.
	 */
	public static String getOrDefault(String value, String defaultValue) {
		return value != null ? value : defaultValue;
	}

	/**
	 * Logs an error message and stack trace for the given throwable.
	 * If the throwable is not an instance of HtException, it also saves the error.
	 *
	 * @param thrown  The throwable to log and possibly save.
	 * @param message The error message to log.
	 */
	public static void error(@NonNull Throwable thrown, String... message) {
		Objects.requireNonNull(thrown, "The throwable cannot be null");

		if (!(thrown instanceof HtException)) {
			ModernDebug.saveError(thrown, message);
		}

		ModernDebug.printStackTrace(thrown);
		ModernLogger.log(ModernLogLevel.SEVERE, message);
	}

	public static String colorize(String message) {
		return ChatColor.translateAlternateColorCodes('&', message);
	}

	/**
	 * Converts a Collection of Strings to an array.
	 * If the input collection is null, returns an empty array.
	 *
	 * @param array The Collection of Strings to convert.
	 * @return The converted array of Strings.
	 */
	public static String[] toArray(final Collection<String> array) {
		return array == null ? new String[0] : array.toArray(new String[0]);
	}

	/**
	 * Converts an array to an ArrayList.
	 * If the input array is null, returns an empty ArrayList.
	 *
	 * @param array The array to convert.
	 * @return The converted ArrayList.
	 */
	@SafeVarargs
	public static <T> ArrayList<T> toList(final T... array) {
		return array == null ? new ArrayList<>() : new ArrayList<>(Arrays.asList(array));
	}

	/**
	 * Converts an Iterable to a List.
	 * If the input Iterable is null, returns an empty List.
	 * Null elements in the Iterable are not added to the List.
	 *
	 * @param it The Iterable to convert.
	 * @return The converted List.
	 */
	public static <T> List<T> toList(final Iterable<T> it) {
		final List<T> list = new ArrayList<>();

		if (it != null)
			it.forEach(el -> {
				if (el != null)
					list.add(el);
			});

		return list;
	}
}