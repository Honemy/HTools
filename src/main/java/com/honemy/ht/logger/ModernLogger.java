package com.honemy.ht.logger;

import com.honemy.ht.plugin.ModernPlugin;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Logger class for the plugin.
 * This class provides methods for logging messages at different levels with optional exception information.
 * It also allows for specifying the source class and method from which the log message originates.
 */
@SuppressWarnings("unused")
public final class ModernLogger {
	/**
	 * The logger instance from Bukkit.
	 */
	private static final Logger LOGGER = Bukkit.getLogger();

	/**
	 * The prefix for the log messages.
	 */
	@Getter
	@Setter
	private static String prefix = "[" + ModernPlugin.getNamed() + "] ";

	/**
	 * Logs a message at a specified level.
	 *
	 * @param level    the level of the message.
	 * @param messages the messages to log.
	 */
	public static void log(Level level, String... messages) {
		log(level, null, null, null, messages);
	}

	/**
	 * Logs a message at a specified level with an exception.
	 *
	 * @param level    the level of the message.
	 * @param thrown   the exception to log.
	 * @param messages the messages to log.
	 */
	public static void log(Level level, Throwable thrown, String... messages) {
		log(level, null, null, thrown, messages);
	}

	/**
	 * Logs a message at a specified level from a specified source class and method.
	 *
	 * @param level        the level of the message.
	 * @param sourceClass  the source class of the message.
	 * @param sourceMethod the source method of the message.
	 * @param messages     the messages to log.
	 */
	public static void log(Level level, String sourceClass, String sourceMethod, String... messages) {
		log(level, sourceClass, sourceMethod, null, messages);
	}

	/**
	 * Logs a message at a specified level from a specified source class and method with an exception.
	 *
	 * @param level        the level of the message.
	 * @param sourceClass  the source class of the message.
	 * @param sourceMethod the source method of the message.
	 * @param thrown       the exception to log.
	 * @param messages     the messages to log.
	 */
	public static void log(Level level, String sourceClass, String sourceMethod, Throwable thrown, String... messages) {
		for (String message : messages) {
			logInternal(level, prefix, sourceClass, sourceMethod, message, thrown);
		}
	}

	/**
	 * Logs a message at a specified level without a prefix.
	 *
	 * @param level    the level of the message.
	 * @param messages the messages to log.
	 */
	public static void logNoPrefix(Level level, String... messages) {
		logNoPrefix(level, null, null, null, messages);
	}

	/**
	 * Logs a message at a specified level without a prefix with an exception.
	 *
	 * @param level    the level of the message.
	 * @param thrown   the exception to log.
	 * @param messages the messages to log.
	 */
	public static void logNoPrefix(Level level, Throwable thrown, String... messages) {
		logNoPrefix(level, null, null, thrown, messages);
	}

	/**
	 * Logs a message at a specified level without a prefix from a specified source class and method.
	 *
	 * @param level        the level of the message.
	 * @param sourceClass  the source class of the message.
	 * @param sourceMethod the source method of the message.
	 * @param messages     the messages to log.
	 */
	public static void logNoPrefix(Level level, String sourceClass, String sourceMethod, String... messages) {
		logNoPrefix(level, sourceClass, sourceMethod, null, messages);
	}

	/**
	 * Logs a message at a specified level without a prefix from a specified source class and method with an exception.
	 *
	 * @param level        the level of the message.
	 * @param sourceClass  the source class of the message.
	 * @param sourceMethod the source method of the message.
	 * @param thrown       the exception to log.
	 * @param messages     the messages to log.
	 */
	public static void logNoPrefix(Level level, String sourceClass, String sourceMethod, Throwable thrown, String... messages) {
		for (String message : messages) {
			logInternal(level, null, sourceClass, sourceMethod, message, thrown);
		}
	}

	/**
	 * Logs a message with a level, prefix, source class, source method, message, and optional exception.
	 *
	 * @param level        the level of the message.
	 * @param prefix       the prefix for the message.
	 * @param sourceClass  the source class of the message.
	 * @param sourceMethod the source method of the message.
	 * @param message      the message to log.
	 * @param thrown       the exception to log.
	 */
	private static void logInternal(Level level, String prefix, String sourceClass, String sourceMethod, String message, Throwable thrown) {
		String prefixedMessage = (prefix == null ? "" : prefix) + message;
		if (sourceClass == null || sourceMethod == null) {
			if (thrown == null) {
				LOGGER.log(level, prefixedMessage);
			} else {
				LOGGER.log(level, prefixedMessage, thrown);
			}
		} else {
			if (thrown == null) {
				LOGGER.logp(level, sourceClass, sourceMethod, prefixedMessage);
			} else {
				LOGGER.logp(level, sourceClass, sourceMethod, prefixedMessage, thrown);
			}
		}
	}
}