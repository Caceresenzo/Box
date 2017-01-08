package box.utils;

public final class NumberUtils {

	public static final char[] NUMBER = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
	
	private NumberUtils() {}
	
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
	
}
