package box.utils;

public class ObjectUtils {
	
	public static Boolean isStringInteger(String string) {
		try {
			Integer.parseInt(string);
		} catch (NumberFormatException exception) {
			return false;
		}
		return true;
	}
	
}