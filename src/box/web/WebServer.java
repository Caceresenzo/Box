package box.web;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.imageio.ImageIO;

import com.google.gson.JsonObject;

import box.TheBox;
import net.sociuris.configuration.ConfigurationSection;
import net.sociuris.http.HttpHeader;
import net.sociuris.http.HttpRequest;
import net.sociuris.http.HttpResponse;
import net.sociuris.http.HttpWebServer;
import net.sociuris.logger.Logger;

public class WebServer extends HttpWebServer {

	public WebServer(ConfigurationSection section) {
		super(section.getProperty("ipAddress").getAsString(), section.getProperty("port").getAsInteger());
	}

	@Override
	public void handleConnection(HttpRequest httpRequest, HttpHeader httpHeader, HttpResponse httpResponse)
			throws IOException {
		//HttpHeader httpHeader = httpResponse.getHeader();
		httpHeader.set("Server", "TheBox v" + TheBox.VERSION);
		String requestUri = httpRequest.getRequestedUri().toLowerCase();
		Logger.getLogger().info("request uri : %s", requestUri);
		if (requestUri.isEmpty()) {
			//httpResponse.setContent(createPage("TheBox", "", "<h1>In development...</h1>"));
		} else if (requestUri.startsWith("api")) {
			httpHeader.setContentType("application/json");

			JsonObject jsonObject = new JsonObject();
			jsonObject.addProperty("status", 200);
			jsonObject.addProperty("version", 1.0);

			httpResponse.setContent(jsonObject.toString());
		} else if (requestUri.equals("favicon.ico")) {
			ByteArrayOutputStream imageByteArray = new ByteArrayOutputStream();
			ImageIO.write(ImageIO.read(TheBox.class.getResource("/box/assets/icon.png")), "png", imageByteArray);
			//imageByteArray.flush();
			httpHeader.setContentType("image/png");
			httpResponse.setContent(imageByteArray.toByteArray(), StandardCharsets.ISO_8859_1);
		}
	}

}
