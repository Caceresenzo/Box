package box.utils;

import java.io.File;
import java.io.IOException;

public class FileUtils {
	
	public static String getExtension(String file) {
		return getExtension(new File(file));
	}
	
	public static String getExtension(File file) {
		String extension = "";
		String filename = file.getName();
		Integer dotpPosition = filename.lastIndexOf(".");
		if (dotpPosition >= 0) {
			extension = filename.substring(dotpPosition);
		}
		return extension.toLowerCase();
	}
	
	public static void createDefaultDirectory(File directory) {
		if(!directory.exists()) {
			directory.mkdirs();
		} else if (!directory.isDirectory()) {
			throw new RuntimeException(directory.getAbsolutePath() + " is not a valid directory!");
		}
	}
	
	public static boolean createDirectoryIfNotExists(File file) {
		if (!file.exists()) {
			return file.mkdirs();
		}
		return false;
	}
	
	public static boolean createFileIfNoteExists(File file) {
		if (!file.exists()) {
			try {
				return file.createNewFile();
			} catch (IOException exception) {
				exception.printStackTrace();
			}
		}
		return false;
	}
	
}