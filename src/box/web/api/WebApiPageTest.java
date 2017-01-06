package box.web.api;

import java.util.regex.Matcher;

import com.google.gson.JsonObject;

import net.sociuris.http.HttpRequest;
import net.sociuris.http.HttpResponse;
import net.sociuris.web.WebSite;

public class WebApiPageTest implements WebApiPage {

	@Override
	public void writePageContent(WebSite webSite, Matcher uriMatcher, HttpRequest request, HttpResponse response, JsonObject jsonObj) {
		jsonObj.addProperty("WebSite", webSite.toString());
		jsonObj.addProperty("Matcher", uriMatcher.toString());
		jsonObj.addProperty("HttpRequest", request.toString());
		jsonObj.addProperty("HttpResponse", response.toString());
	}

}
