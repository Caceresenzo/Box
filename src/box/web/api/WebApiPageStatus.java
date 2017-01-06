package box.web.api;

import com.google.gson.JsonObject;

import box.TheBox;
import net.sociuris.http.HttpRequest;
import net.sociuris.http.HttpResponse;
import net.sociuris.web.WebSite;

public class WebApiPageStatus implements WebApiPage {

	@Override
	public void writePageContent(WebSite webSite, HttpRequest request, HttpResponse response, JsonObject jsonObj) {
		jsonObj.addProperty("version", "TheBox API v" + TheBox.VERSION);
	}

}
