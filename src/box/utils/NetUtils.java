package box.utils;

import java.io.IOException;
import java.net.ServerSocket;

import box.TheBox;

public class NetUtils {
	
	public static Short getAvailablePort() {
		short port = -1;
		do {
			try {
				port = (short) TheBox.RANDOM.nextInt(65534);
				ServerSocket serverSocket = new ServerSocket(port);
				serverSocket.close();
			} catch (IOException e) {
				port = -1;
			}
		} while (port == -1);
		return port;
	}
	
	public static Boolean isPortOpen(Integer port) {
		ServerSocket socket = null;
		try {
			socket = new ServerSocket(port);
			socket.setReuseAddress(true);
			return true;
		} catch (IOException exception) {
			exception.printStackTrace();
		} finally {
			if (socket != null) {
				try {
					socket.close();
				} catch (IOException exception0) {
					exception0.printStackTrace();
				}
			}
		}
		return false;
	}
	
}