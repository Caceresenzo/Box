package box.utils;

import java.util.ArrayList;
import java.util.List;

public class CollectionUtils {

	public static <T> List<T> arrayToList(T[] arr) {
		List<T> list = new ArrayList<T>();
		for (T t : arr)
			list.add(t);
		return list;
	}

	@SuppressWarnings("unchecked")
	public static <T extends Object> T[] listToArray(List<T> list) {
		Object[] arr = new Object[list.size()];
		for (int i = 0; i < list.size(); i++)
			arr[i] = list.get(i);
		return (T[]) arr;
	}

	@SuppressWarnings("unchecked")
	public static <T extends Object> T[] mergeArray(T[]... arrays) {
		List<T> list = new ArrayList<T>();
		for(T[] array : arrays)
			for(T value : array)
				list.add(value);
		return list.toArray((T[])new Object[list.size()]);
	}

}
