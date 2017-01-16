package box.web.api;

import java.util.regex.Matcher;

import net.sociuris.http.HttpConnection;
import net.sociuris.http.HttpRequest;
import net.sociuris.http.HttpResponse;
import net.sociuris.http.HttpStatusCode;
import net.sociuris.json.JsonObject;
import net.sociuris.web.WebSite;

public class WebApiErrorPage implements WebApiPage {

	@Override
	public void writePageContent(WebSite webSite, Matcher uriMatcher, HttpConnection connection, HttpRequest request, HttpResponse response, JsonObject jsonObj) {
		HttpStatusCode statusCode = response.getStatusCode();

		jsonObj.addProperty("code", statusCode.getCode());
		jsonObj.addProperty("error", statusCode.getName());
		String message = statusCode.getMessage();
		if (!message.isEmpty())
			jsonObj.addProperty("message", message);
	}

}
