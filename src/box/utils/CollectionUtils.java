package box.utils;

import java.util.ArrayList;
import java.util.List;

public class CollectionUtils {
	
	public static List<String> arrayToString(String[] array) {
		List<String> list = getNewList();
		for (String string : array) {
			list.add(string);
		}
		return list;
	}
	
	public static List<String> getNewList() {
		return new ArrayList<String>();
	}
	
}