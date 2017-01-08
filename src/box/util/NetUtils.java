package box.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.URL;
import java.net.URLConnection;

public class NetUtils {

	private NetUtils() {
	}

	public static int getAvailablePort() {
		int port = -1;
		do {
			try {
				port = (RandomUtils.RANDOM.nextInt(65533) + 1);
				new ServerSocket(port).close();
			} catch (IOException e) {
				port = -1;
			}
		} while (port == -1);
		return port;
	}

	public static boolean isLocalPortFree(int port) {
		try {
			new ServerSocket(port).close();
			return true;
		} catch (IOException e) {
			return false;
		}
	}

	public static String urlToString(URL url) throws IOException {
		URLConnection urlConnection = url.openConnection();
		InputStream inputStream = urlConnection.getInputStream();
		String result = StringUtils.inputStreamToString(inputStream);
		inputStream.close();
		return result;
	}

}