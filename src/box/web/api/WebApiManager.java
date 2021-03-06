package box.web.api;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sociuris.http.HttpConnection;
import net.sociuris.http.HttpRequest;
import net.sociuris.http.HttpResponse;
import net.sociuris.http.HttpResponseHeader;
import net.sociuris.http.HttpStatusCode;
import net.sociuris.json.JsonObject;
import net.sociuris.web.WebSite;

public class WebApiManager {

	private final Map<Pattern, WebApiPage> webPageMap = new HashMap<Pattern, WebApiPage>();
	private final WebApiErrorPage errorPage = new WebApiErrorPage();

	public WebApiManager() {
		this.webPageMap.put(Pattern.compile("/(status)?"), new WebApiPageStatus());
		this.webPageMap.put(Pattern.compile("/authentification"), new WebApiPageAuthentication());
	}

	public String handleConnection(WebSite webSite, Matcher uriMatcher, HttpConnection connection, HttpRequest request, HttpResponse response) {
		String requestUri = request.getRequestedUri().substring(4);
		Matcher lastMatcher = null;
		WebApiPage webPage = null;
		for (Entry<Pattern, WebApiPage> entry : webPageMap.entrySet()) {
			lastMatcher = entry.getKey().matcher(requestUri);
			if (lastMatcher.matches())
				webPage = entry.getValue();
		}

		if (webPage == null) {
			response.setStatusCode(HttpStatusCode.CLIENT_ERROR_NOT_FOUND);
			webPage = errorPage;
		}

		HttpResponseHeader responseHeader = response.getHeader();
		responseHeader.setContentType("application/json");
		responseHeader.setCharset(StandardCharsets.UTF_8);

		JsonObject jsonObj = new JsonObject();
		webPage.writePageContent(webSite, uriMatcher, connection, request, response, jsonObj);
		return jsonObj.toString();

	}

}