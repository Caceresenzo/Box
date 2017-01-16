package box.web.api;

import java.util.regex.Matcher;

import net.sociuris.http.HttpConnection;
import net.sociuris.http.HttpRequest;
import net.sociuris.http.HttpResponse;
import net.sociuris.json.JsonObject;
import net.sociuris.web.WebSite;

public interface WebApiPage {

	public void writePageContent(WebSite webSite, Matcher uriMatcher, HttpConnection connection, HttpRequest request, HttpResponse response, JsonObject jsonObj);
	
}