package box.resources;

import java.io.InputStream;
import java.net.URL;

public class ResourcesManager {

	private ResourcesManager() {
	}

	public static InputStream getResourceAsStream(String resourceName) {
		if (resourceName.startsWith("/"))
			resourceName = resourceName.substring(1);
		return ResourcesManager.class.getResourceAsStream(resourceName);
	}

	public static URL getResourceAsURL(String resourceName) {
		if (resourceName.startsWith("/"))
			resourceName = resourceName.substring(1);
		return ResourcesManager.class.getResource(resourceName);
	}

}