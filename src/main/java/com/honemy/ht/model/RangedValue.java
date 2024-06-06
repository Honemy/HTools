package com.honemy.ht.model;

import com.honemy.ht.RandomUtil;
import com.honemy.ht.Valid;
import lombok.Getter;

/**
 * Represents a ranged value with a minimum and maximum bound.
 */
@SuppressWarnings("unused")
@Getter
public final class RangedValue {

	/**
	 * The minimum value of the range.
	 */
	private final Number min;

	/**
	 * The maximum value of the range.
	 */
	private final Number max;

	/**
	 * Constructs a new RangedValue object with the same minimum and maximum values.
	 *
	 * @param value The value for both the minimum and maximum bounds.
	 */
	public RangedValue(Number value) {
		this(value, value);
	}

	/**
	 * Constructs a new RangedValue object with the specified minimum and maximum bounds.
	 *
	 * @param min The minimum value of the range.
	 * @param max The maximum value of the range.
	 */
	public RangedValue(Number min, Number max) {
		Valid.checkBoolean(min.longValue() <= max.longValue(), "Minimum must be lower or equal maximum");

		this.min = min;
		this.max = max;
	}

	/**
	 * Retrieves the minimum value of the range as a double.
	 *
	 * @return The minimum value of the range as a double.
	 */
	public double getMinDouble() {
		return min.doubleValue();
	}

	/**
	 * Retrieves the maximum value of the range as a double.
	 *
	 * @return The maximum value of the range as a double.
	 */
	public double getMaxDouble() {
		return max.doubleValue();
	}

	/**
	 * Retrieves the minimum value of the range as a long.
	 *
	 * @return The minimum value of the range as a long.
	 */
	public long getMinLong() {
		return min.longValue();
	}

	/**
	 * Retrieves the maximum value of the range as a long.
	 *
	 * @return The maximum value of the range as a long.
	 */
	public long getMaxLong() {
		return max.longValue();
	}

	/**
	 * Checks if the given long value falls within the range.
	 *
	 * @param value The long value to check.
	 * @return True if the value is within the range, false otherwise.
	 */
	public boolean isInRangeLong(long value) {
		return value >= min.longValue() && value <= max.longValue();
	}

	/**
	 * Checks if the given double value falls within the range.
	 *
	 * @param value The double value to check.
	 * @return True if the value is within the range, false otherwise.
	 */
	public boolean isInRangeDouble(double value) {
		return value >= min.doubleValue() && value <= max.doubleValue();
	}

	/**
	 * Generates a random integer within the range.
	 *
	 * @return A random integer within the range.
	 */
	public int getRandomInt() {
		return RandomUtil.nextBetween((int) getMinLong(), (int) getMaxLong());
	}

	/**
	 * Checks if the range represents a static value (minimum equals maximum).
	 *
	 * @return True if the range is static, false otherwise.
	 */
	public boolean isStatic() {
		return min.longValue() == max.longValue();
	}

	/**
	 * Converts the ranged value to a string representation.
	 *
	 * @return The string representation of the ranged value.
	 */
	public String toLine() {
		return min.longValue() + " - " + max.longValue();
	}

	/**
	 * Parses a string representation of a ranged value into a RangedValue object.
	 *
	 * @param line The string representation of the ranged value.
	 * @return The parsed RangedValue object.
	 */
	public static RangedValue parse(String line) {
		line = line.replaceAll("\\s", "");

		String[] parts = line.split("-");
		Valid.checkBoolean(parts.length == 1 || parts.length == 2, "Malformed value " + line);

		String first = parts[0].trim();
		String second = parts.length == 2 ? parts[1].trim() : first;

		Valid.checkBoolean(Valid.isNumber(first), "Invalid ranged value: " + first);
		Valid.checkBoolean(Valid.isNumber(second), "Invalid ranged value: " + second);

		Number firstNumber = first.contains(".") ? Double.parseDouble(first) : Long.parseLong(first);
		Number secondNumber = second.contains(".") ? Double.parseDouble(second) : Long.parseLong(second);

		Valid.checkBoolean(firstNumber.doubleValue() <= secondNumber.doubleValue(),
				"First number cannot be greater than second: " + firstNumber + " vs " + secondNumber);

		return new RangedValue(firstNumber, secondNumber);
	}

	/**
	 * Converts the ranged value to a string representation.
	 *
	 * @return The string representation of the ranged value.
	 */
	@Override
	public String toString() {
		return isStatic() ? String.valueOf(min.longValue()) : min.longValue() + " - " + max.longValue();
	}
}
