package box.web;

import java.util.regex.Matcher;

import box.web.api.WebApiManager;
import net.sociuris.http.HttpRequest;
import net.sociuris.http.HttpResponse;
import net.sociuris.web.WebPage;
import net.sociuris.web.WebSite;

public class WebPageApi implements WebPage {

	private final WebApiManager apiManager = new WebApiManager();

	@Override
	public String writePageContent(WebSite webSite, Matcher uriMatcher,  HttpRequest request, HttpResponse response) {
		return apiManager.handleConnection(webSite, uriMatcher, request, response);
	}

}