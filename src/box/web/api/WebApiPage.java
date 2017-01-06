package box.web.api;

import com.google.gson.JsonObject;

import net.sociuris.http.HttpRequest;
import net.sociuris.http.HttpResponse;
import net.sociuris.web.WebSite;

public interface WebApiPage {

	public void writePageContent(WebSite webSite, HttpRequest request, HttpResponse response, JsonObject jsonObj);
	
}