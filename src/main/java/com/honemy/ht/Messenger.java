package com.honemy.ht;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.UtilityClass;
import org.bukkit.command.CommandSender;

@UtilityClass
public final class Messenger {

	/**
	 * The prefix send while sending info message
	 */
	@Setter
	@Getter
	private String infoPrefix = "&8&l[&9&li&8&l]&7 ";

	/**
	 * The prefix send while sending success message
	 */
	@Setter
	@Getter
	private String successPrefix = "&8&l[&2&l+&8&l]&7 ";

	/**
	 * The prefix send while sending warning message
	 */
	@Setter
	@Getter
	private String warnPrefix = "&8&l[&6&l!&8&l]&6 ";

	/**
	 * The prefix send while sending error message
	 */
	@Setter
	@Getter
	private String errorPrefix = "&8&l[&4&lX&8&l]&c ";

	/**
	 * The prefix send while sending questions
	 */
	@Setter
	@Getter
	private String questionPrefix = "&8&l[&a&l?&l&8]&7 ";

	/**
	 * The prefix send while sending announcements
	 */
	@Setter
	@Getter
	private String announcePrefix = "&8&l[&5&l!&l&8]&d ";

	/**
	 * Sends an info message to the player
	 *
	 * @param player  the player to send the message to
	 * @param message the message to send
	 */
	public void info(final CommandSender player, final String message) {
		tell(player, infoPrefix, message);
	}

	/**
	 * Sends a success message to the player
	 *
	 * @param player  the player to send the message to
	 * @param message the message to send
	 */
	public void success(final CommandSender player, final String message) {
		tell(player, successPrefix, message);
	}

	/**
	 * Sends a warning message to the player
	 *
	 * @param player  the player to send the message to
	 * @param message the message to send
	 */
	public void warn(final CommandSender player, final String message) {
		tell(player, warnPrefix, message);
	}

	/**
	 * Sends an error message to the player
	 *
	 * @param player   the player to send the message to
	 * @param messages the messages to send
	 */
	public void error(final CommandSender player, final String... messages) {
		for (final String message : messages)
			error(player, message);
	}

	/**
	 * Sends an error message to the player
	 *
	 * @param player  the player to send the message to
	 * @param message the message to send
	 */
	public void error(final CommandSender player, final String message) {
		tell(player, errorPrefix, message);
	}

	/**
	 * Sends a question message to the player
	 *
	 * @param player  the player to send the message to
	 * @param message the message to send
	 */
	public void question(final CommandSender player, final String message) {
		tell(player, questionPrefix, message);
	}

	/**
	 * Sends an announcement message to the player
	 *
	 * @param player  the player to send the message to
	 * @param message the message to send
	 */
	public void announce(final CommandSender player, final String message) {
		tell(player, announcePrefix, message);
	}

	/**
	 * Sends a message to the player
	 *
	 * @param sender  the player to send the message to
	 * @param prefix  the prefix to use
	 * @param message the message to send
	 */
	private void tell(CommandSender sender, String prefix, String message) {
		if (message.isEmpty() || "none".equals(message))
			return;

		sender.sendMessage(Common.colorize(prefix + message));
	}
}
