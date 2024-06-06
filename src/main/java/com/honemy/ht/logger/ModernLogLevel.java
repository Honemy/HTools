package com.honemy.ht.logger;

import lombok.Getter;

import java.util.logging.Level;

/**
 * Enum representing the different levels of logging.
 * Each level is associated with a Level instance from java.util.logging.Level.
 */
@Getter
public enum ModernLogLevel {
	/**
	 * Severe level, indicating a serious failure.
	 */
	SEVERE(Level.SEVERE),

	/**
	 * Warning level, indicating a potential problem.
	 */
	WARNING(Level.WARNING),

	/**
	 * Info level, indicating informational messages.
	 */
	INFO(Level.INFO),

	/**
	 * Config level, indicating static configuration messages.
	 */
	CONFIG(Level.CONFIG),

	/**
	 * Fine level, indicating tracing information.
	 */
	FINE(Level.FINE);

	/**
	 * The associated Level instance.
	 */
	private final Level level;

	/**
	 * Constructor for the ModernLogLevel.
	 *
	 * @param level the Level instance associated with this log level.
	 */
	ModernLogLevel(Level level) {
		this.level = level;
	}
}