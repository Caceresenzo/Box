package box.web;

import java.io.IOException;

import net.sociuris.configuration.ConfigurationSection;
import net.sociuris.http.HttpHeader;
import net.sociuris.http.HttpRequest;
import net.sociuris.http.HttpResponse;
import net.sociuris.http.HttpWebServer;
import net.sociuris.util.HtmlUtils;

public class WebServer extends HttpWebServer {

	public WebServer(ConfigurationSection section) {
		super(section.getProperty("ipAddress").getAsString(), section.getProperty("port").getAsInteger());
	}

	@Override
	public void handleConnection(HttpRequest httpRequest, HttpHeader httpHeader, HttpResponse httpResponse)
			throws IOException {
		
		httpResponse.setContent(HtmlUtils.createHtmlPage("TheBox", "<meta charset=\"UTF-8\">", "<h1>In development...</h1>"));
		
	}

}
