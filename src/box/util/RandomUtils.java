package box.util;

import java.util.Random;

public class RandomUtils {

	public static final Random RANDOM = new Random();
	
	public static String getString(int length) {
		final Character[] chars = CollectionUtils.mergeArray(Character.class, StringUtils.LOWERCASE_ALPHABET, StringUtils.UPPERCASE_ALPHABET);
		String result = "";
		while(length > 0) {
			result += chars[RandomUtils.RANDOM.nextInt(chars.length)];
			length--;
		}
		return result;
	}
	
	public static int getInteger() {
		return RandomUtils.getInteger(Integer.MIN_VALUE, Integer.MAX_VALUE);
	}
	
	public static int getInteger(int min, int max) {
		return RandomUtils.RANDOM.nextInt(max - min) + min;
	}
	
	public static double getDouble() {
		return RandomUtils.getDouble(Double.MIN_VALUE, Double.MAX_VALUE);
	}
	
	public static double getDouble(double min, double max) {
		double value = 0D;
		for(int i = 0; i < (max - min); i++)
			value += RANDOM.nextDouble();
		return value + min;
	}

}