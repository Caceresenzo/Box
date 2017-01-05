package box.web;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import com.google.gson.JsonObject;

import box.TheBox;
import net.sociuris.configuration.ConfigurationSection;
import net.sociuris.http.HttpRequest;
import net.sociuris.http.HttpResponse;
import net.sociuris.http.HttpResponseHeader;
import net.sociuris.http.HttpStatusCode;
import net.sociuris.http.HttpWebServer;
import net.sociuris.util.HtmlUtils;

public class WebServer extends HttpWebServer {

	public WebServer(ConfigurationSection section) {
		super(section.getProperty("ipAddress").getAsString(), section.getProperty("port").getAsInteger());
	}

	@Override
	public void handleConnection(HttpRequest httpRequest, HttpResponse httpResponse)
			throws IOException {
		HttpResponseHeader httpHeader = httpResponse.getHeader();
		httpHeader.set("Server", "TheBox v" + TheBox.VERSION);
		
		String requestUri = httpRequest.getRequestedUri().toLowerCase();
		
		if (requestUri.startsWith("/api")) {
			
			httpHeader.setContentType("application/json", StandardCharsets.UTF_8);
			
			JsonObject jsonObject = new JsonObject();
			jsonObject.addProperty("status", 200);
			jsonObject.addProperty("version", 1.0);

			httpResponse.setContent(jsonObject.toString());
			
		} else if (requestUri.equals("/favicon.ico")) {
			
			InputStream inputStream = TheBox.class.getResourceAsStream("/box/assets/icon.png");
			byte[] imageByteArray = new byte[inputStream.available()];
			inputStream.read(imageByteArray);
			httpResponse.setContent(imageByteArray, StandardCharsets.UTF_8);
			httpHeader.setContentType("image/png", StandardCharsets.UTF_8);
			
		}
		else {
			
			httpResponse.setContent(HtmlUtils.generatePage(HttpStatusCode.SUCCESS_OK));
			
		}
	}

}
