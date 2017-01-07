package box.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
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
	
	public static Boolean createDefaultDirectory(File directory) {
		if(!directory.exists()) {
			return directory.mkdirs();
		} else if (!directory.isDirectory()) {
			throw new RuntimeException(directory.getAbsolutePath() + " is not a valid directory!");
		}
		return false;
	}
	
	public static Boolean createDefaultFile(File file) {
		if (!file.exists()) {
			try {
				return file.createNewFile();
			} catch (IOException exception) {
				exception.printStackTrace();
			}
		} else if (!file.isFile()) {
			throw new RuntimeException(file.getAbsolutePath() + " is not a valid file!");
		}
		return false;
	}
	
	public static Boolean writeInFile(File file, String[] text) {
		createDefaultFile(file);
		BufferedWriter writer;
		try {
			writer = new BufferedWriter(new FileWriter(file));
			for (String line : text) {
				writer.write(line);
			}
			writer.close();
			return true;
		} catch (IOException exception) {
			exception.printStackTrace();
			return false;
		}
	}
	
}