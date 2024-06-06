package com.honemy.ht;

import com.honemy.ht.logger.ModernLogger;
import com.honemy.ht.plugin.ModernPlugin;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.logging.Level;

/**
 * Utility class for file operations.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class FileUtil {

	/**
	 * Returns an existing file or creates a new one if it does not exist.
	 *
	 * @param path The path of the file.
	 * @return The file at the specified path.
	 */
	public static File getOrCreateFile(String path) {
		Path filePath = getPath(path);
		if (Files.notExists(filePath)) {
			createFile(filePath);
		}
		return filePath.toFile();
	}

	/**
	 * Returns a file at the specified path.
	 *
	 * @param path The path of the file.
	 * @return The file at the specified path.
	 */
	public static File getFile(String path) {
		return getPath(path).toFile();
	}

	/**
	 * Writes the specified contents to the file.
	 *
	 * @param file     The file to write to.
	 * @param contents The contents to write.
	 */
	public static void writeToFile(File file, String contents) {
		try {
			Files.write(file.toPath(), contents.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
		} catch (IOException e) {
			ModernLogger.log(Level.WARNING, e, "Failed to write to file " + file.getPath());
		}
	}

	/**
	 * Returns a Path object representing the specified path.
	 *
	 * @param path The path string.
	 * @return The Path object.
	 */
	private static Path getPath(String path) {
		return Paths.get(ModernPlugin.getData().getPath(), path);
	}

	/**
	 * Creates a new file at the specified path.
	 *
	 * @param filePath The path of the file to create.
	 */
	private static void createFile(Path filePath) {
		createDataFolder();
		try {
			Files.createFile(filePath);
		} catch (IOException e) {
			ModernLogger.log(Level.WARNING, e, "Failed to create file " + filePath);
		}
	}

	/**
	 * Creates the data folder if it does not exist.
	 */
	private static void createDataFolder() {
		Path dataFolderPath = ModernPlugin.getData().toPath();
		if (Files.notExists(dataFolderPath)) {
			try {
				Files.createDirectory(dataFolderPath);
			} catch (IOException e) {
				ModernLogger.log(Level.WARNING, e, "Failed to create data folder " + dataFolderPath);
			}
		}
	}
}