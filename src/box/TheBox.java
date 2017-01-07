package box;

import java.util.regex.Pattern;

import box.web.WebPageApi;
import box.web.WebPagePanel;
import net.sociuris.configuration.ConfigurationFile;
import net.sociuris.configuration.ConfigurationSection;
import net.sociuris.web.WebSite;

public class TheBox {

	public static final String VERSION = "0.1b";
	public static ConfigurationFile PROPERTIES = null;

	private static TheBox instance;

	public static TheBox get() {
		return TheBox.instance;
	}

	// private final Logger logger = Logger.getLogger();
	private final WebSite webSite;
	
	public TheBox(ConfigurationFile configurationFile) {
		TheBox.PROPERTIES = configurationFile;

		ConfigurationSection webServerSection = configurationFile.getSection("webServer");
		this.webSite = new WebSite(webServerSection.getProperty("ipAddress").getAsString(),
				webServerSection.getProperty("port").getAsInteger());
		this.webSite.start();
		
		this.webSite.addPage(Pattern.compile("/?"), new WebPagePanel());
		this.webSite.addPage(Pattern.compile("/api/(\\w)*"), new WebPageApi());

		TheBox.instance = this;
	}

	public WebSite getWebSite() {
		return webSite;
	}

}