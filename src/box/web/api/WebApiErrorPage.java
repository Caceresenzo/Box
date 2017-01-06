package box.web.api;

import com.google.gson.JsonObject;

import net.sociuris.http.HttpRequest;
import net.sociuris.http.HttpResponse;
import net.sociuris.http.HttpStatusCode;
import net.sociuris.web.WebSite;

public class WebApiErrorPage implements WebApiPage {

	@Override
	public void writePageContent(WebSite webSite, HttpRequest request, HttpResponse response, JsonObject jsonObj) {
		HttpStatusCode statusCode = response.getStatusCode();

		jsonObj.addProperty("code", statusCode.getCode());
		jsonObj.addProperty("error", statusCode.getName());
		String message = statusCode.getMessage();
		if (message != null)
			jsonObj.addProperty("message", message);
	}

}
