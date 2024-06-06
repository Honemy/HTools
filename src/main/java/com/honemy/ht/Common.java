package com.honemy.ht;

import com.honemy.ht.debug.ModernDebug;
import com.honemy.ht.exception.HtException;
import com.honemy.ht.logger.ModernLogger;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bukkit.ChatColor;

import java.util.Objects;
import java.util.logging.Level;

/**
 * Utility class for common operations.
 */
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
		ModernLogger.log(Level.SEVERE, message);
	}

	public static String colorize(String message) {
		if (message == null || "none".equals(message))
			return "";

		return ChatColor.translateAlternateColorCodes('&', message);
	}
}