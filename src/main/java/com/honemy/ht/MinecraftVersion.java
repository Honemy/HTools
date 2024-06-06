package com.honemy.ht;

import com.honemy.ht.logger.ModernLogLevel;
import com.honemy.ht.logger.ModernLogger;
import lombok.Getter;
import org.bukkit.Bukkit;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Class representing a Minecraft version.
 * This class provides methods for comparing and retrieving version information.
 */
@SuppressWarnings("unused")
public final class MinecraftVersion {

	/**
	 * The server version.
	 */
	private static final String serverVersion;

	/**
	 * The current version.
	 */
	@Getter
	private static final V current;

	/**
	 * The subversion.
	 */
	@Getter
	private static final int subversion;

	/**
	 * Enum representing a Minecraft version.
	 */
	public enum V {
		v1_20(20),
		v1_19(19),
		v1_18(18),
		v1_17(17),
		v1_16(16),
		v1_15(15),
		v1_14(14),
		v1_13(13),
		v1_12(12),
		v1_11(11),
		v1_10(10),
		v1_9(9),
		v1_8(8),
		v1_7(7),
		v1_6(6),
		v1_5(5),
		v1_4(4),
		v1_3_AND_BELOW(3);

		private final int minorVersionNumber;

		private static final Map<Integer, V> BY_MINOR_VERSION = new HashMap<>();

		static {
			for (V v : values()) {
				BY_MINOR_VERSION.put(v.minorVersionNumber, v);
			}
		}

		V(int version) {
			this.minorVersionNumber = version;
		}

		/**
		 * Parses a version number into a V enum.
		 *
		 * @param number the version number.
		 * @return the corresponding V enum.
		 */
		private static Optional<V> parse(int number) {
			return Optional.ofNullable(BY_MINOR_VERSION.get(number));
		}

		@Override
		public String toString() {
			return "1." + this.minorVersionNumber;
		}
	}

	/**
	 * Checks if the current version equals the specified version.
	 *
	 * @param version the version to compare with.
	 * @return true if the versions are equal, false otherwise.
	 */
	public static boolean equals(V version) {
		return compareWith(version) == 0;
	}

	/**
	 * Checks if the current version is older than the specified version.
	 *
	 * @param version the version to compare with.
	 * @return true if the current version is older, false otherwise.
	 */
	public static boolean olderThan(V version) {
		return compareWith(version) < 0;
	}

	/**
	 * Checks if the current version is newer than the specified version.
	 *
	 * @param version the version to compare with.
	 * @return true if the current version is newer, false otherwise.
	 */
	public static boolean newerThan(V version) {
		return compareWith(version) > 0;
	}

	/**
	 * Checks if the current version is at least the specified version.
	 *
	 * @param version the version to compare with.
	 * @return true if the current version is at least the specified version, false otherwise.
	 */
	public static boolean atLeast(V version) {
		return compareWith(version) >= 0;
	}

	/**
	 * Compares the current version with the specified version.
	 *
	 * @param version the version to compare with.
	 * @return a negative integer if the current version is older, zero if they are equal, or a positive integer if the current version is newer.
	 */
	private static int compareWith(V version) {
		try {
			return Integer.compare(getCurrent().minorVersionNumber, version.minorVersionNumber);
		} catch (final Throwable t) {
			ModernLogger.log(ModernLogLevel.SEVERE, t, "An error occurred while comparing versions");
			return 0;
		}
	}

	/**
	 * Returns the full version string.
	 *
	 * @return the full version string.
	 */
	public static String getFullVersion() {
		return current.toString() + (subversion > 0 ? "." + subversion : "");
	}

	/**
	 * Returns the server version.
	 *
	 * @return the server version.
	 */
	@Deprecated
	public static String getServerVersion() {
		return serverVersion.equals("craftbukkit") ? "" : serverVersion;
	}

	static {
		// Find NMS package version
		final String packageName = Bukkit.getServer().getClass().getPackage().getName();
		final String curr = packageName.substring(packageName.lastIndexOf('.') + 1);
		serverVersion = !"craftbukkit".equals(curr) && !packageName.isEmpty() ? curr : "";

		// Find the Bukkit version
		final String bukkitVersion = Bukkit.getServer().getBukkitVersion(); // 1.20.6-R0.1-SNAPSHOT
		final String versionString = bukkitVersion.split("-")[0]; // 1.20.6
		final String[] versions = versionString.split("\\.");

		Valid.checkBoolean(!(versions.length == 2 || versions.length == 3), "HTools cannot read Bukkit version: " + versionString + ", expected 2 or 3 parts separated by dots, got " + versions.length + " parts");

		final int version = Integer.parseInt(versions[1]);

		current = version < 3 ? V.v1_3_AND_BELOW : V.parse(version).orElseThrow(() -> new RuntimeException("Invalid version number: " + version));
		subversion = versions.length == 3 ? Integer.parseInt(versions[2]) : 0;
	}
}