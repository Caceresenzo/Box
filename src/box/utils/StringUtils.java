package box.utils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class StringUtils {

	private StringUtils() {}
	
	public static <T> String arrayToString(T[] array) {
		StringBuilder builder = new StringBuilder();
		int size = array.length;
		for(int i = 0; i < size; i++) {
			builder.append(array[i].toString());
			if(i + 1 < size)
				builder.append(',');			
		}
		return "[" + builder.toString() + "]";	
	}
	
	public static <T> String listToString(List<T> list) {
		StringBuilder builder = new StringBuilder();
		int size = list.size();
		for(int i = 0; i < size; i++) {
			builder.append(list.get(i).toString());
			if(i + 1 < size)
				builder.append(',');			
		}
		return "[" + builder.toString() + "]";		
	}

	public static <T,V> String mapToString(Map<T, V> map) {
		StringBuilder builder = new StringBuilder();
		int i = 0;
		int size = map.size();
		for(Entry<? extends Object, ? extends Object> entry : map.entrySet()) {
			i++;
			builder.append(entry.getKey().toString()).append('=').append(entry.getValue().toString());
			if(i < size)
				builder.append(',');
		}
		return "[" + builder.toString() + "]";		
	}
	
	public static String inputStreamToString(InputStream inputStream) throws IOException {
		byte[] receivedBytes = new byte[inputStream.available()];
		inputStream.read(receivedBytes, 0, inputStream.available());
		return new String(receivedBytes, StandardCharsets.UTF_8);
	}

	public static String replaceFirst(String text, String target, String replacement) {
		for (int i = 0; i < text.length(); i++) {
			if (text.substring(i).startsWith(target))
				return text.substring(0, i) + replacement + text.substring(i + target.length());
		}
		return text;
	}
	
}