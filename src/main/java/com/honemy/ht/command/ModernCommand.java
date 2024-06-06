package com.honemy.ht.command;

import com.honemy.ht.Common;
import com.honemy.ht.Messenger;
import com.honemy.ht.ReflectionUtil;
import com.honemy.ht.exception.CommandException;
import com.honemy.ht.logger.ModernLogLevel;
import com.honemy.ht.logger.ModernLogger;
import com.honemy.ht.remain.Remain;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;

/**
 * Abstract class representing a modern command.
 * This class extends Command and provides methods for command execution.
 */
@SuppressWarnings("unused")
public abstract class ModernCommand extends Command {

	/**
	 * The sender of the command.
	 */
	protected CommandSender sender;

	/**
	 * The label of the command.
	 */
	protected String commandLabel;

	/**
	 * The arguments of the command.
	 */
	protected String[] args;

	/**
	 * The method to register the command.
	 */
	private Method registerMethod;

	/**
	 * The command map.
	 */
	private Object commandMap;

	/**
	 * Constructor for a command with a name.
	 *
	 * @param name the name of the command.
	 */
	protected ModernCommand(String name) {
		this(name, new String[0]);
	}

	/**
	 * Constructor for a command with a name and aliases.
	 *
	 * @param name    the name of the command.
	 * @param aliases the aliases of the command.
	 */
	protected ModernCommand(String name, String... aliases) {
		super(name);
		this.setAliases(Arrays.asList(aliases));
		this.initialize();
	}

	/**
	 * Checks if the command sender is a console.
	 *
	 * @throws CommandException if the sender is a console.
	 */
	protected void checkConsole() throws CommandException {
		if (!(this.isPlayer())) {
			throw new CommandException("This command can only be executed by players.");
		}
	}

	protected final boolean isPlayer() {
		return this.sender instanceof Player;
	}

	/**
	 * Initializes the command by getting the command map and register method.
	 */
	private void initialize() {
		try {
			commandMap = Remain.getCommandMap();
			registerMethod = Remain.getRegisterMethod(commandMap);
		} catch (NoSuchFieldException | IllegalAccessException | NoSuchMethodException e) {
			ModernLogger.log(ModernLogLevel.SEVERE, e, "Failed to initialize command: " + this.getName());
		}
	}

	/**
	 * Executes the command.
	 *
	 * @param sender       the sender of the command.
	 * @param commandLabel the label of the command.
	 * @param args         the arguments of the command.
	 * @return false.
	 */
	@Override
	public boolean execute(@Nonnull CommandSender sender, @Nonnull String commandLabel, @Nonnull String[] args) {
		this.sender = sender;
		this.commandLabel = commandLabel;
		this.args = args;

		try {
			this.onCommand();
		} catch (CommandException e) {
			handleCommandException(e);
		} catch (Throwable t) {
			Common.error(t, "An error occurred while executing command: " + this.commandLabel);
		}
		return false;
	}

	/**
	 * Handles a command exception by sending messages to the sender.
	 *
	 * @param e the command exception.
	 */
	private void handleCommandException(CommandException e) {
		if (e.getMessages() != null) {
			if (this.sender instanceof Player) {
				Messenger.error(this.sender, e.getMessages());
			} else {
				ModernLogger.log(ModernLogLevel.WARNING, e.getMessages());
			}
		}
	}

	/**
	 * Registers the command.
	 */
	public void register() {
		unregister();
		try {
			registerMethod.invoke(commandMap, this.getName(), this);
		} catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
			ModernLogger.log(ModernLogLevel.SEVERE, e, "Failed to register command: " + this.getName());
		}
	}

	/**
	 * Unregisters the command.
	 */
	public void unregister() {
		try {
			@SuppressWarnings("unchecked")
			Map<String, Command> knownCommands = (Map<String, Command>) ReflectionUtil.getPrivateField(commandMap, "knownCommands");
			knownCommands.remove(getName());
			for (String alias : getAliases()) {
				if (knownCommands.containsKey(alias) && knownCommands.get(alias).toString().contains(this.getName())) {
					knownCommands.remove(alias);
				}
			}
		} catch (Exception e) {
			ModernLogger.log(ModernLogLevel.SEVERE, e, "Failed to unregister command: " + this.getName());
		}
	}

	/**
	 * Abstract method to be implemented by subclasses for command execution.
	 */
	protected abstract void onCommand();
}
