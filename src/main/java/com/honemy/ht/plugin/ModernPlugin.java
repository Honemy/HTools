package com.honemy.ht.plugin;

import com.honemy.ht.MinecraftVersion;
import com.honemy.ht.MinecraftVersion.V;
import com.honemy.ht.command.ModernCommand;
import com.honemy.ht.debug.ModernDebug;
import com.honemy.ht.logger.ModernLogger;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.logging.Level;

/**
 * Abstract class representing a modern plugin.
 * This class extends JavaPlugin and implements Listener.
 */
public abstract class ModernPlugin extends JavaPlugin implements Listener {

	/**
	 * The instance of the plugin.
	 */
	private static ModernPlugin instance;

	/**
	 * Shortcut for getDescription().getName()
	 */
	@Getter
	private static String named;

	/**
	 * Shortcut for getDescription().getVersion()
	 */
	@Getter
	private static String version;

	/**
	 * Shortcut for getDataFolder()
	 */
	@Getter
	private static File data;

	/**
	 * Returns the instance of the plugin.
	 * If the instance is null, it tries to get the plugin instance.
	 * If the plugin instance cannot be retrieved, it logs a severe message and throws an exception.
	 *
	 * @return the instance of the plugin.
	 */
	public static ModernPlugin getInstance() {
		if (instance == null) {
			try {
				instance = JavaPlugin.getPlugin(ModernPlugin.class);
			} catch (IllegalStateException ex) {
				if (Bukkit.getPluginManager().getPlugin("PlugMan") != null) {
					ModernLogger.log(Level.SEVERE, "Failed to get instance of the plugin. If you reloaded using PlugMan, you need to restart the server.");
				}
				throw ex;
			}
		}
		return instance;
	}

	/**
	 * Checks if the plugin instance exists.
	 *
	 * @return true if the instance is not null, false otherwise.
	 */
	public static boolean hasInstance() {
		return instance != null;
	}

	/**
	 * Called when the plugin is loaded.
	 * Tries to get the instance of the plugin and sets the name, version, and data folder of the plugin.
	 */
	@Override
	public final void onLoad() {
		if (instance == null) {
			try {
				instance = JavaPlugin.getPlugin(ModernPlugin.class);
			} catch (IllegalStateException ex) {
				if (MinecraftVersion.olderThan(V.v1_7)) {
					instance = this;
				} else {
					throw ex;
				}
			}
		}

		named = this.getDescription().getName();
		version = this.getDescription().getVersion();
		data = this.getDataFolder();

		this.onPluginLoad();
	}

	/**
	 * Called when the plugin is enabled.
	 */
	@Override
	public final void onEnable() {
		try {
			this.onPluginStart();
		} catch (Throwable t) {
			ModernDebug.printStackTrace(t);
		}
	}

	/**
	 * Called when the plugin is disabled.
	 */
	@Override
	public final void onDisable() {
		this.onPluginStop();
	}

	/**
	 * Registers a command for this plugin.
	 *
	 * @param command the command to register.
	 */
	protected void registerCommand(final ModernCommand command) {
		command.register();
	}

	/**
	 * Unregisters a command for this plugin.
	 *
	 * @param command the command to unregister.
	 */
	protected void unregisterCommand(final ModernCommand command) {
		command.unregister();
	}

	/**
	 * Called when the plugin is loaded.
	 * Can be overridden by subclasses to provide specific behavior.
	 */
	protected void onPluginLoad() {
		// Can be overridden by subclasses
	}

	/**
	 * Called when the plugin is started.
	 * Must be overridden by subclasses to provide specific behavior.
	 */
	protected abstract void onPluginStart();

	/**
	 * Called when the plugin is stopped.
	 * Can be overridden by subclasses to provide specific behavior.
	 */
	protected void onPluginStop() {
		// Can be overridden by subclasses
	}
}
