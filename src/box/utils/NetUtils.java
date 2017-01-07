package box.utils;

import java.io.IOException;
import java.net.ServerSocket;

import box.TheBox;

public class NetUtils {

	private NetUtils() {
	}

	public static int getAvailablePort() {
		int port = -1;
		do {
			try {
				port = (TheBox.RANDOM.nextInt(65533) + 1);
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

}