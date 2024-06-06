package com.honemy.ht.debug;

import com.honemy.ht.Common;
import com.honemy.ht.FileUtil;
import com.honemy.ht.exception.HtException;
import com.honemy.ht.logger.ModernLogLevel;
import com.honemy.ht.logger.ModernLogger;
import com.honemy.ht.plugin.ModernPlugin;
import lombok.NonNull;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Utility class for debugging operations.
 */
public final class ModernDebug {

	/**
	 * Saves the error to a file and logs the error message and stack trace.
	 *
	 * @param thrown   The throwable to save and log.
	 * @param messages The error messages to save.
	 */
	public static void saveError(Throwable thrown, String... messages) {
		String systemInfo = "Running " + Bukkit.getName() + " " + Bukkit.getBukkitVersion() + " and Java " + System.getProperty("java.version");

		try {
			List<String> lines = new ArrayList<>();
			String header = ModernPlugin.getNamed() + " " + ModernPlugin.getVersion() + " encountered " + thrown.getClass().getSimpleName();
			String pluginsList = Arrays.toString(Bukkit.getPluginManager().getPlugins());

			lines.add("----------------------------------------------------------------------------------------------");
			lines.add(header);
			lines.add(systemInfo);
			lines.add("Plugins: " + pluginsList);
			lines.add("----------------------------------------------------------------------------------------------");

			if (messages != null && messages.length > 0 && !String.join("", messages).isEmpty()) {
				lines.add("\nMore Information:");
				lines.addAll(Arrays.asList(messages));
			}

			addStackTrace(lines, thrown);

			lines.add("----------------------------------------------------------------------------------------------");
			lines.add(System.lineSeparator());

			ModernLogger.log(ModernLogLevel.SEVERE, header + "! Please check your error.txt and report this issue with the information in that file. " + systemInfo);
			FileUtil.writeToFile(FileUtil.getOrCreateFile("error.txt"), String.join("\n", lines));
		} catch (Throwable secondError) {
			ModernLogger.log(ModernLogLevel.SEVERE, secondError, "Got error when saving error! Saving error:");
			ModernLogger.log(ModernLogLevel.SEVERE, thrown, "Original error that is not saved:");
		}
	}

	/**
	 * Adds the stack trace of the throwable to the given list.
	 *
	 * @param lines  The list to add the stack trace to.
	 * @param thrown The throwable to get the stack trace from.
	 */
	private static void addStackTrace(List<String> lines, Throwable thrown) {
		while (thrown != null) {
			lines.add(thrown.getClass().getSimpleName() + " " + Common.getOrDefault(thrown.getMessage(), "(Unknown cause)"));

			int count = 0;
			for (StackTraceElement el : thrown.getStackTrace()) {
				if (count > 6 && el.getClassName().startsWith("net.minecraft.server")) {
					break;
				}
				if (!el.getClassName().contains("sun.reflect")) {
					lines.add("\tat " + el);
				}
				count++;
			}
			thrown = thrown.getCause();
		}
	}

	/**
	 * Logs the stack trace of the given throwable.
	 *
	 * @param throwable The throwable to log the stack trace of.
	 */
	public static void printStackTrace(@NonNull Throwable throwable) {
		List<Throwable> causes = new ArrayList<>();
		Throwable cause = throwable.getCause();

		while (cause != null) {
			causes.add(cause);
			cause = cause.getCause();
		}

		if (throwable instanceof HtException && !causes.isEmpty()) {
			print(throwable.getMessage());
		} else {
			print(throwable.toString());
			printStackTraceElements(throwable);
		}

		if (!causes.isEmpty()) {
			Throwable lastCause = causes.get(causes.size() - 1);
			print(lastCause.toString());
			printStackTraceElements(lastCause);
		}
	}

	/**
	 * Logs the stack trace elements of the given throwable.
	 *
	 * @param throwable The throwable to log the stack trace elements of.
	 */
	private static void printStackTraceElements(Throwable throwable) {
		for (StackTraceElement element : throwable.getStackTrace()) {
			String line = element.toString();
			if (canPrint(line)) {
				print("\tat " + line);
			}
		}
	}

	/**
	 * Checks if the message can be printed.
	 *
	 * @param message The message to check.
	 * @return True if the message can be printed, false otherwise.
	 */
	private static boolean canPrint(String message) {
		return !(message.contains("net.minecraft") ||
				message.contains("org.bukkit.craftbukkit") ||
				message.contains("org.github.paperspigot.ServerScheduler") ||
				message.contains("nashorn") ||
				message.contains("javax.script") ||
				message.contains("org.yaml.snakeyaml") ||
				message.contains("sun.reflect") ||
				message.contains("sun.misc") ||
				message.contains("java.lang.Thread.run") ||
				message.contains("java.util.concurrent.ThreadPoolExecutor"));
	}

	/**
	 * Prints the specified message to the console.
	 *
	 * @param message The message to print.
	 */
	private static void print(String message) {
		Bukkit.getConsoleSender().sendMessage(message);
	}
}
