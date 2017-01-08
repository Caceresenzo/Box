package box.util;

public final class NumberUtils {

	public static final char[] NUMBER = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };

	private NumberUtils() {
	}

	public static boolean isInteger(String s) {
		try {
			Integer.parseInt(s);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public static boolean isDouble(String s) {
		try {
			Double.parseDouble(s);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public static boolean isLong(String s) {
		try {
			Long.parseLong(s);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public static boolean isShort(String s) {
		try {
			Short.parseShort(s);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public static boolean isBoolean(String s) {
		if (s.equalsIgnoreCase("true") || s.equalsIgnoreCase("false"))
			return true;
		else
			return false;
	}

	public static boolean inRange(int min, int max, int value) {
		return (value > min && value < max);
	}

	public static boolean inRange(double min, double max, double value) {
		return (value > min && value < max);
	}

	public static int setInRange(int min, int max, int value) {
		return (int) NumberUtils.setInRange((double) min, (double) max, (double) value);
	}

	public static double setInRange(double min, double max, double value) {
		if (min <= max) {
			if (value > max)
				value = max;
			else if (value < min)
				value = min;
			return value;
		} else
			throw new IllegalArgumentException("Min value is greater than max value!");
	}

}
