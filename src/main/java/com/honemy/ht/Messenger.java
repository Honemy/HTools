package com.honemy.ht;

import lombok.experimental.UtilityClass;
import org.bukkit.command.CommandSender;

import java.util.EnumMap;
import java.util.Map;

/**
 * Utility class for sending messages with different types and prefixes.
 */
@UtilityClass
public final class Messenger {

	/**
	 * Enum representing different types of messages.
	 */
	public enum MessageType {
		INFO,
		SUCCESS,
		WARN,
		ERROR,
		QUESTION,
		ANNOUNCE
	}

	// Map to store prefixes for different message types
	private static final Map<MessageType, String> prefixes = new EnumMap<>(MessageType.class);

	static {
		// Initialize prefixes for each message type
		prefixes.put(MessageType.INFO, "&8&l[&9&li&8&l]&7 ");
		prefixes.put(MessageType.SUCCESS, "&8&l[&2&l+&8&l]&7 ");
		prefixes.put(MessageType.WARN, "&8&l[&6&l!&8&l]&6 ");
		prefixes.put(MessageType.ERROR, "&8&l[&4&lX&8&l]&c ");
		prefixes.put(MessageType.QUESTION, "&8&l[&a&l?&l&8]&7 ");
		prefixes.put(MessageType.ANNOUNCE, "&8&l[&5&l!&l&8]&d ");
	}

	/**
	 * Set a new prefix for a specific message type.
	 *
	 * @param type   The type of the message.
	 * @param prefix The new prefix.
	 */
	public void setPrefix(MessageType type, String prefix) {
		prefixes.put(type, prefix);
	}

	/**
	 * Get the prefix for a specific message type.
	 *
	 * @param type The type of the message.
	 * @return The prefix for the specified message type.
	 */
	public String getPrefix(MessageType type) {
		return prefixes.get(type);
	}

	/**
	 * Send a message of a specific type to a command sender.
	 *
	 * @param sender   The command sender to send the message to.
	 * @param type     The type of the message.
	 * @param messages The messages to send.
	 */
	private void sendMessage(CommandSender sender, MessageType type, String... messages) {
		String prefix = getPrefix(type);
		for (String message : messages) {
			if (message.isEmpty() || "none".equals(message))
				continue;
			sender.sendMessage(Common.colorize(prefix + message));
		}
	}

	/**
	 * Send an info message to a command sender.
	 *
	 * @param player   The command sender to send the message to.
	 * @param messages The messages to send.
	 */
	public void info(CommandSender player, String... messages) {
		sendMessage(player, MessageType.INFO, messages);
	}

	/**
	 * Send a success message to a command sender.
	 *
	 * @param player   The command sender to send the message to.
	 * @param messages The messages to send.
	 */
	public void success(CommandSender player, String... messages) {
		sendMessage(player, MessageType.SUCCESS, messages);
	}

	/**
	 * Send a warning message to a command sender.
	 *
	 * @param player   The command sender to send the message to.
	 * @param messages The messages to send.
	 */
	public void warn(CommandSender player, String... messages) {
		sendMessage(player, MessageType.WARN, messages);
	}

	/**
	 * Send an error message to a command sender.
	 *
	 * @param player   The command sender to send the message to.
	 * @param messages The messages to send.
	 */
	public void error(CommandSender player, String... messages) {
		sendMessage(player, MessageType.ERROR, messages);
	}

	/**
	 * Send a question message to a command sender.
	 *
	 * @param player   The command sender to send the message to.
	 * @param messages The messages to send.
	 */
	public void question(CommandSender player, String... messages) {
		sendMessage(player, MessageType.QUESTION, messages);
	}

	/**
	 * Send an announcement message to a command sender.
	 *
	 * @param player   The command sender to send the message to.
	 * @param messages The messages to send.
	 */
	public void announce(CommandSender player, String... messages) {
		sendMessage(player, MessageType.ANNOUNCE, messages);
	}
}