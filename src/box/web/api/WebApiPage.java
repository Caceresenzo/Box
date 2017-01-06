package box.web.api;

import java.util.regex.Matcher;

import com.google.gson.JsonObject;

import net.sociuris.http.HttpRequest;
import net.sociuris.http.HttpResponse;
import net.sociuris.web.WebSite;

public interface WebApiPage {

	public void writePageContent(WebSite webSite, Matcher uriMatcher, HttpRequest request, HttpResponse response, JsonObject jsonObj);
	
}