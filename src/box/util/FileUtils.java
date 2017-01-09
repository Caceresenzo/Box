package box.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import net.sociuris.logger.Logger;

public class FileUtils {

	private static final Logger LOGGER = Logger.getLogger();

	private FileUtils() {
	}

	public static String getExtension(File file) {
		return FileUtils.getExtension(file.getName());
	}

	public static String getExtension(String fileName) {
		String extension = null;
		int dotPosition = fileName.lastIndexOf(".");
		if (dotPosition != -1)
			extension = fileName.substring(dotPosition + 1);
		return extension;
	}

	public static boolean createDefaultDirectory(File directory) {
		if (!directory.exists())
			return directory.mkdirs();
		return false;
	}

	public static boolean createDefaultFile(File file) {
		if (!file.exists()) {
			try {
				return file.createNewFile();
			} catch (IOException e) {
				LOGGER.printStackTrace(e);
			}
		}
		return false;
	}

	public static boolean writeInFile(File file, String... texts) {
		FileUtils.createDefaultFile(file);
		try {
			FileWriter fileWriter = new FileWriter(file);
			for (String text : texts)
				fileWriter.write(text);
			fileWriter.close();
			return true;
		} catch (IOException e) {
			LOGGER.printStackTrace(e);
			return false;
		}
	}

}
