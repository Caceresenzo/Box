package box.utils;

import java.util.Random;

public class RandomUtils {

	public static String getRandomString(Integer length) {
		final String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJLMNOPQRSTUVWXYZ1234567890_"; //"abcdefghijklmnopqrstuvwxyzABCDEFGHIJLMNOPQRSTUVWXYZ1234567890!@#$%^&*()_+";
		StringBuilder result = new StringBuilder();
		while (length > 0) {
			Random random = new Random();
			result.append(characters.charAt(random.nextInt(characters.length())));
			length--;
		}
		return result.toString();
	}
	
	public static Integer getRandomInt(Integer min, Integer max) {
		return getRandomInt(min, max, new Random());
	}
	
	public static Integer getRandomInt(Integer min, Integer max, Random random) {
		return random.nextInt(max - min) + min;
	}
}