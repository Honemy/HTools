package com.honemy.ht;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bukkit.*;

import java.util.List;
import java.util.Random;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Utility class providing various random generation methods.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RandomUtil {

	/**
	 * The random instance used for generating random values.
	 */
	@Getter
	private static final Random random = new Random();

	/**
	 * The array of color and decoration codes.
	 */
	private static final char[] COLORS_AND_DECORATION = {
			'0', '1', '2', '3', '4',
			'5', '6', '7', '8', '9',
			'a', 'b', 'c', 'd', 'e',
			'f', 'k', 'l', 'n', 'o'
	};

	/**
	 * The array of chat colors.
	 */
	private static final char[] CHAT_COLORS = {
			'0', '1', '2', '3', '4',
			'5', '6', '7', '8', '9',
			'a', 'b', 'c', 'd', 'e',
			'f'
	};

	/**
	 * The array of letters.
	 */
	private static final char[] LETTERS = {
			'a', 'b', 'c', 'd', 'e',
			'f', 'g', 'h', 'i', 'j',
			'k', 'l', 'm', 'n', 'o',
			'p', 'q', 'r', 's', 't',
			'u', 'v', 'w', 'y', 'z',
	};

	/**
	 * Determines if a given percentage chance occurs.
	 *
	 * @param percent the percentage chance (0-100)
	 * @return true if the chance occurs, false otherwise
	 */
	public static boolean chance(final double percent) {
		return random.nextDouble() * 100 < percent;
	}

	/**
	 * Gets a random dye color.
	 *
	 * @return a random {@link DyeColor}
	 */
	public static DyeColor nextDyeColor() {
		return DyeColor.values()[random.nextInt(DyeColor.values().length)];
	}

	/**
	 * Gets a random color or decoration code.
	 *
	 * @return a random color or decoration code as a String
	 */
	public static String nextColorOrDecoration() {
		return "&" + COLORS_AND_DECORATION[nextInt(COLORS_AND_DECORATION.length)];
	}

	/**
	 * Generates a random string of the specified length using letters.
	 *
	 * @param length the length of the string
	 * @return a random string
	 */
	public static String nextString(int length) {
		StringBuilder text = new StringBuilder(length);
		for (int i = 0; i < length; i++) {
			text.append(LETTERS[nextInt(LETTERS.length)]);
		}
		return text.toString();
	}

	/**
	 * Gets a random chat color.
	 *
	 * @return a random {@link ChatColor}
	 */
	public static ChatColor nextChatColor() {
		return ChatColor.getByChar(CHAT_COLORS[nextInt(CHAT_COLORS.length)]);
	}

	/**
	 * Gets a random predefined color.
	 *
	 * @return a random {@link Color}
	 */
	public static Color nextColor() {
		return nextItem(Color.AQUA, Color.ORANGE, Color.WHITE, Color.YELLOW, Color.RED, Color.GREEN, Color.BLUE);
	}

	/**
	 * Generates a random integer between the specified bounds (inclusive).
	 *
	 * @param min the minimum value
	 * @param max the maximum value
	 * @return a random integer between min and max
	 */
	public static int nextBetween(final int min, final int max) {
		Valid.checkBoolean(min <= max, "Min !< max");
		return min + nextInt(max - min + 1);
	}

	/**
	 * Generates a random integer with the specified upper bound (exclusive).
	 *
	 * @param boundExclusive the upper bound (exclusive)
	 * @return a random integer between 0 (inclusive) and boundExclusive (exclusive)
	 */
	public static int nextInt(final int boundExclusive) {
		Valid.checkBoolean(boundExclusive > 0, "Getting a random number must have the bound above 0, got: " + boundExclusive);
		return random.nextInt(boundExclusive);
	}

	/**
	 * Generates a random boolean value.
	 *
	 * @return a random boolean value
	 */
	public static boolean nextBoolean() {
		return random.nextBoolean();
	}

	/**
	 * Selects a random item from the given array.
	 *
	 * @param items the array of items
	 * @param <T>   the type of the items
	 * @return a random item from the array
	 */
	@SafeVarargs
	public static <T> T nextItem(final T... items) {
		return items[nextInt(items.length)];
	}

	/**
	 * Selects a random item from the given iterable.
	 *
	 * @param items the iterable of items
	 * @param <T>   the type of the items
	 * @return a random item from the iterable
	 */
	public static <T> T nextItem(final Iterable<T> items) {
		return nextItem(items, null);
	}

	/**
	 * Selects a random item from the given iterable that satisfies the specified condition.
	 *
	 * @param items     the iterable of items
	 * @param condition the condition to test the items
	 * @param <T>       the type of the items
	 * @return a random item from the iterable that satisfies the condition
	 */
	public static <T> T nextItem(final Iterable<T> items, final Predicate<T> condition) {
		List<T> list = StreamSupport.stream(items.spliterator(), false)
				.filter(item -> condition == null || condition.test(item))
				.collect(Collectors.toList());

		Valid.checkBoolean(!list.isEmpty(), "Filtered list is empty");

		return list.get(nextInt(list.size()));
	}

	/**
	 * Generates a random location around the origin within the specified radius.
	 *
	 * @param origin the origin location
	 * @param radius the radius
	 * @param is3D   whether to generate a 3D location
	 * @return a random location around the origin
	 */
	public static Location nextLocation(final Location origin, final double radius, final boolean is3D) {
		return nextLocation(origin, 0, radius, is3D);
	}

	/**
	 * Generates a random location around the origin within the specified min and max radius.
	 *
	 * @param origin    the origin location
	 * @param minRadius the minimum radius
	 * @param maxRadius the maximum radius
	 * @param is3D      whether to generate a 3D location
	 * @return a random location around the origin
	 */
	public static Location nextLocation(final Location origin, final double minRadius, final double maxRadius, final boolean is3D) {
		Valid.checkBoolean(maxRadius > 0 && minRadius >= 0, "Max radius must be over 0 and min radius must be non-negative");
		Valid.checkBoolean(maxRadius > minRadius, "Max radius must be greater than min radius");

		double radius = minRadius + (maxRadius - minRadius) * random.nextDouble();
		double theta = 2 * Math.PI * random.nextDouble();
		double offsetX = radius * Math.cos(theta);
		double offsetZ = radius * Math.sin(theta);
		double offsetY = is3D ? (random.nextDouble() - 0.5) * radius * 2 : 0;

		return origin.clone().add(offsetX, offsetY, offsetZ);
	}

	/**
	 * Generates a random X-coordinate within the given chunk.
	 *
	 * @param chunk the chunk
	 * @return a random X-coordinate within the chunk
	 */
	public static int nextChunkX(final Chunk chunk) {
		return (chunk.getX() << 4) + nextInt(16);
	}

	/**
	 * Generates a random Z-coordinate within the given chunk.
	 *
	 * @param chunk the chunk
	 * @return a random Z-coordinate within the chunk
	 */
	public static int nextChunkZ(final Chunk chunk) {
		return (chunk.getZ() << 4) + nextInt(16);
	}
}
