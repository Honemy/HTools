package com.honemy.ht;

import com.honemy.ht.exception.HtException;
import com.honemy.ht.model.RangedValue;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bukkit.util.Vector;

import java.util.*;
import java.util.regex.Pattern;

@SuppressWarnings("unused")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Valid {

	private static final Pattern PATTERN_INTEGER = Pattern.compile("-?\\d+");
	private static final Pattern PATTERN_DECIMAL = Pattern.compile("([0-9]+\\.?[0-9]*|\\.[0-9]+)");


	/**
	 * Checks if the provided object is not null.
	 *
	 * @param toCheck the object to check
	 * @throws HtException if the object is null
	 */
	public static void checkNotNull(final Object toCheck) {
		if (toCheck == null) {
			throw new HtException();
		}
	}

	/**
	 * Checks if the provided object is not null, with a custom exception message.
	 *
	 * @param toCheck      the object to check
	 * @param falseMessage the exception message if the object is null
	 * @throws HtException if the object is null
	 */
	public static void checkNotNull(final Object toCheck, final String falseMessage) {
		if (toCheck == null) {
			throw new HtException(falseMessage);
		}
	}

	/**
	 * Checks if the provided boolean expression is true.
	 *
	 * @param expression the boolean expression to check
	 * @throws HtException if the expression is false
	 */
	public static void checkBoolean(final boolean expression) {
		if (!expression) {
			throw new HtException();
		}
	}

	/**
	 * Checks if the provided boolean expression is true, with a custom exception message.
	 *
	 * @param expression   the boolean expression to check
	 * @param falseMessage the exception message if the expression is false
	 * @param replacements the replacements for the message format
	 * @throws HtException if the expression is false
	 */
	public static void checkBoolean(final boolean expression, final String falseMessage, final Object... replacements) {
		if (!expression) {
			String message = falseMessage;
			try {
				message = String.format(falseMessage, replacements);
			} catch (final Throwable ignored) {
			}
			throw new HtException(message);
		}
	}

	/**
	 * Checks if the provided string is a valid integer.
	 *
	 * @param toCheck      the string to check
	 * @param falseMessage the exception message if the string is not a valid integer
	 * @param replacements the replacements for the message format
	 * @throws HtException if the string is not a valid integer
	 */
	public static void checkInteger(final String toCheck, final String falseMessage, final Object... replacements) {
		if (!isInteger(toCheck)) {
			throw new HtException(String.format(falseMessage, replacements));
		}
	}

	/**
	 * Checks if the provided map is not empty.
	 *
	 * @param collection the map to check
	 * @param message    the exception message if the map is empty
	 * @throws IllegalArgumentException if the map is empty
	 */
	public static void checkNotEmpty(final Map<?, ?> collection, final String message) {
		if (collection == null || collection.isEmpty()) {
			throw new IllegalArgumentException(message);
		}
	}

	/**
	 * Checks if the provided collection is not empty.
	 *
	 * @param collection the collection to check
	 * @param message    the exception message if the collection is empty
	 * @throws IllegalArgumentException if the collection is empty
	 */
	public static void checkNotEmpty(final Collection<?> collection, final String message) {
		if (collection == null || collection.isEmpty()) {
			throw new IllegalArgumentException(message);
		}
	}

	/**
	 * Checks if the provided string is not empty.
	 *
	 * @param message      the string to check
	 * @param emptyMessage the exception message if the string is empty
	 * @throws IllegalArgumentException if the string is empty
	 */
	public static void checkNotEmpty(final String message, final String emptyMessage) {
		if (message == null || message.isEmpty()) {
			throw new IllegalArgumentException(emptyMessage);
		}
	}

	/**
	 * Checks if the provided string is a valid integer.
	 *
	 * @param raw the string to check
	 * @return true if the string is a valid integer, false otherwise
	 */
	public static boolean isInteger(final String raw) {
		checkNotNull(raw, "Cannot check if null is an integer!");
		return PATTERN_INTEGER.matcher(raw).matches();
	}

	/**
	 * Checks if the provided string is a valid decimal.
	 *
	 * @param raw the string to check
	 * @return true if the string is a valid decimal, false otherwise
	 */
	public static boolean isDecimal(final String raw) {
		checkNotNull(raw, "Cannot check if null is a decimal!");
		return PATTERN_DECIMAL.matcher(raw).matches();
	}

	/**
	 * Checks if the provided string is a valid number.
	 *
	 * @param raw the string to check
	 * @return true if the string is a valid number, false otherwise
	 */
	public static boolean isNumber(@NonNull final String raw) {
		checkNotNull(raw, "Cannot check if null is a Number!");
		if (raw.isEmpty()) {
			return false;
		}

		final char[] letters = raw.toCharArray();
		int length = letters.length;
		boolean hasExp = false;
		boolean hasDecPoint = false;
		boolean allowSigns = false;
		boolean foundDigit = false;

		final int start = (letters[0] == '-') ? 1 : 0;

		if (length > start + 1 && letters[start] == '0' && letters[start + 1] == 'x') {
			int i = start + 2;
			if (i == length) return false;
			for (; i < letters.length; i++) {
				if (!Character.isDigit(letters[i]) && "abcdefABCDEF".indexOf(letters[i]) == -1) return false;
			}
			return true;
		}

		length--;
		int i = start;

		while (i < length || (i < length + 1 && allowSigns && !foundDigit)) {
			char currentChar = letters[i];
			if (Character.isDigit(currentChar)) {
				foundDigit = true;
				allowSigns = false;
			} else if (currentChar == '.') {
				if (hasDecPoint || hasExp) return false;
				hasDecPoint = true;
			} else if (currentChar == 'e' || currentChar == 'E') {
				if (hasExp || !foundDigit) return false;
				hasExp = true;
				allowSigns = true;
			} else if (currentChar == '+' || currentChar == '-') {
				if (!allowSigns) return false;
				allowSigns = false;
				foundDigit = false;
			} else {
				return false;
			}
			i++;
		}

		if (i < letters.length) {
			char lastChar = letters[i];
			if (Character.isDigit(lastChar)) return true;
			if (lastChar == 'e' || lastChar == 'E') return false;
			if (lastChar == '.') return !hasDecPoint && !hasExp && foundDigit;
			if (!allowSigns && "dDfF".indexOf(lastChar) != -1) return foundDigit;
			if ("lL".indexOf(lastChar) != -1) return foundDigit && !hasExp;
			return false;
		}

		return !allowSigns && foundDigit;
	}

	/**
	 * Checks if the provided collection is null or empty.
	 *
	 * @param array the collection to check
	 * @return true if the collection is null or empty, false otherwise
	 */
	public static boolean isNullOrEmpty(final Collection<?> array) {
		return array == null || array.isEmpty();
	}

	/**
	 * Checks if the values in the provided map are all null or if the map itself is null.
	 *
	 * @param map the map to check
	 * @return true if the map is null or all its values are null, false otherwise
	 */
	public static boolean isNullOrEmptyValues(final Map<?, ?> map) {
		if (map == null)
			return true;

		for (final Object value : map.values())
			if (value != null)
				return false;

		return true;
	}

	/**
	 * Checks if the provided array is null or empty.
	 *
	 * @param array the array to check
	 * @return true if the array is null or empty, false otherwise
	 */
	public static boolean isNullOrEmpty(final Object[] array) {
		if (array != null)
			for (final Object object : array)
				if (object instanceof String) {
					if (!((String) object).isEmpty())
						return false;
				} else if (object != null)
					return false;

		return true;
	}

	/**
	 * Checks if the provided string is null or empty.
	 *
	 * @param message the string to check
	 * @return true if the string is null or empty, false otherwise
	 */
	public static boolean isNullOrEmpty(final String message) {
		return message == null || message.isEmpty();
	}

	/**
	 * Checks if the provided Vector has finite values for its components.
	 *
	 * @param vector the Vector to check
	 * @return true if all components of the Vector are finite, false otherwise
	 */
	public static boolean isFinite(final Vector vector) {
		return Double.isFinite(vector.getX()) && Double.isFinite(vector.getY()) && Double.isFinite(vector.getZ());
	}

	/**
	 * Checks if a long value is within a specified range.
	 *
	 * @param value  the value to check
	 * @param ranged the range to check against
	 * @return true if the value is within the range, false otherwise
	 */
	public static boolean isInRange(final long value, final RangedValue ranged) {
		return value >= ranged.getMinLong() && value <= ranged.getMaxLong();
	}

	/**
	 * Checks if a double value is within a specified range.
	 *
	 * @param value the value to check
	 * @param min   the minimum value of the range
	 * @param max   the maximum value of the range
	 * @return true if the value is within the range, false otherwise
	 */
	public static boolean isInRange(final double value, final double min, final double max) {
		return value >= min && value <= max;
	}

	/**
	 * Checks if a long value is within a specified range.
	 *
	 * @param value the value to check
	 * @param min   the minimum value of the range
	 * @param max   the maximum value of the range
	 * @return true if the value is within the range, false otherwise
	 */
	public static boolean isInRange(final long value, final long min, final long max) {
		return value >= min && value <= max;
	}

	/**
	 * Checks if the provided object is a UUID.
	 *
	 * @param object the object to check
	 * @return true if the object is a UUID, false otherwise
	 */
	public static boolean isUUID(final Object object) {
		if (object instanceof String) {
			final String[] components = object.toString().split("-");
			return components.length == 5;
		}
		return object instanceof UUID;
	}

	/**
	 * Checks if all values in the provided collection are equal.
	 *
	 * @param values the collection of values to check
	 * @return true if all values are equal, false otherwise
	 */
	public static boolean valuesEqual(final Collection<String> values) {
		final List<String> copy = new ArrayList<>(values);
		String lastValue = null;

		for (final String value : copy) {
			if (lastValue == null)
				lastValue = value;

			if (!lastValue.equals(value))
				return false;

			lastValue = value;
		}

		return true;
	}

	/**
	 * Checks if a string is present in a list, ignoring case and leading slashes.
	 *
	 * @param element     the string to search for
	 * @param list        the list to search in
	 * @param isBlacklist true if the search list is a blacklist, false otherwise
	 * @return true if the string is found in the list, false otherwise
	 */
	public static boolean isInList(final String element, final boolean isBlacklist, final Iterable<String> list) {
		return isBlacklist == Valid.isInList(element, list);
	}

	/**
	 * Checks if a string is present in a list, ignoring case and leading slashes.
	 *
	 * @param element the string to search for
	 * @param list    the list to search in
	 * @return true if the string is found in the list, false otherwise
	 */
	public static boolean isInList(final String element, final Iterable<String> list) {
		try {
			for (final String matched : list)
				if (removeSlash(element).equalsIgnoreCase(removeSlash(matched)))
					return true;
		} catch (final
		ClassCastException ex) { // for example when YAML translates "yes" to "true" to boolean (!) (#wontfix)
		}
		return false;
	}

	/**
	 * Checks if the specified element starts with any of the elements in the provided list.
	 *
	 * @param element The element to check.
	 * @param list    The list of strings to compare against.
	 * @return {@code true} if the element starts with any of the elements in the list, {@code false} otherwise.
	 */
	public static boolean isInListStartsWith(final String element, final Iterable<String> list) {
		try {
			for (final String matched : list)
				if (removeSlash(element).toLowerCase().startsWith(removeSlash(matched).toLowerCase()))
					return true;
		} catch (final ClassCastException ex) {
			// Ignore ClassCastException caused by YAML translation issues.
		}
		return false;
	}

	/**
	 * Checks if the specified element matches any of the constants in the provided enum.
	 *
	 * @param element     The element to check.
	 * @param enumeration The array of Enum constants to compare against.
	 * @return {@code true} if the element matches any of the enum constants (case-insensitive), {@code false} otherwise.
	 */
	public static boolean isInListEnum(final String element, final Enum<?>[] enumeration) {
		for (final Enum<?> constant : enumeration)
			if (constant.name().equalsIgnoreCase(element))
				return true;
		return false;
	}

	/**
	 * Removes leading slash ('/') character from the given string if it exists.
	 *
	 * @param message The input string.
	 * @return The string with the leading slash removed.
	 */
	private static String removeSlash(final String message) {
		return message.startsWith("/") ? message.substring(1) : message;
	}
}
