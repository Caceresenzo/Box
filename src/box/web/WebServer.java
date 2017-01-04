package box.web;

import java.io.IOException;

import net.sociuris.configuration.ConfigurationSection;
import net.sociuris.http.HttpHeader;
import net.sociuris.http.HttpRequest;
import net.sociuris.http.HttpResponse;
import net.sociuris.http.HttpStatusCode;
import net.sociuris.http.HttpWebServer;

public class WebServer extends HttpWebServer {

	public WebServer(ConfigurationSection section) {
		super(section.getProperty("ipAddress").getAsString(), section.getProperty("port").getAsInteger());
	}

	@Override
	public void handleConnection(HttpRequest httpRequest, HttpHeader httpHeader, HttpResponse httpResponse) throws IOException {
		httpResponse.setStatusCode(HttpStatusCode.SUCCESS_OK);
	}

}
