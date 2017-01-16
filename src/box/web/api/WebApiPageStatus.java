package box.web.api;

import java.util.regex.Matcher;

import box.TheBox;
import net.sociuris.http.HttpConnection;
import net.sociuris.http.HttpRequest;
import net.sociuris.http.HttpResponse;
import net.sociuris.json.JsonObject;
import net.sociuris.web.WebSite;

public class WebApiPageStatus implements WebApiPage {

	@Override
	public void writePageContent(WebSite webSite, Matcher uriMatcher, HttpConnection connection, HttpRequest request,
			HttpResponse response, JsonObject jsonObj) {
		jsonObj.addProperty("version", "TheBox API v" + TheBox.VERSION);
	}

}
