package box.web;

import java.util.regex.Matcher;

import net.sociuris.http.HttpRequest;
import net.sociuris.http.HttpResponse;
import net.sociuris.web.WebPage;
import net.sociuris.web.WebSite;

public class WebPagePanel implements WebPage {

	public static final String STYLESHEET = "";

	@Override
	public String writePageContent(WebSite webSite, Matcher uriMatcher, HttpRequest request, HttpResponse response) {		
		return "<!DOCTYPE html><html><head><title>TheBox v</title><link href=\"https://fonts.googleapis.com/icon?family=Material+Icons\" rel=\"stylesheet\"><style>    @charset \"UTF-8\";@import url(https://fonts.googleapis.com/css?family=Roboto:300,400,700);body{font-family:Roboto,sans-serif;background-color:#36393E;color:#FAFAFA;margin:0}span.loader{display:block;border:5px solid #F3F3F3;border-top:5px solid #555;border-radius:50%;animation:spin 1s linear infinite;-webkit-animation:spin 1s linear infinite;width:50px;height:50px}@keyframes spin{0%{transform:rotate(0)}100%{transform:rotate(360deg)}}div.error-container{display:block;text-align:center;color:#F44336}div.error-container>span.material-icons{font-size:3em}div.error-container>p{font-size:1.2em;margin:2px 0}div.container{background-color:#282B30;padding:10px 20px;margin:10px;-webkit-box-shadow:0 0 10px 0 rgba(0,0,0,.2);box-shadow:0 0 10px 0 rgba(0,0,0,.2)}div#scene-container{position:absolute;top:75px;left:300px;right:0;box-sizing:border-box}header.fixed-header{position:fixed;left:0;top:0;right:0;background-color:#1E2124;border-bottom-color:#212121;width:100%;height:75px;border-bottom-width:1px;border-bottom-style:solid;-webkit-box-shadow:0 0 10px 5px rgba(0,0,0,.2);box-shadow:0 0 10px 5px rgba(0,0,0,.2);z-index:999}header.fixed-header>h1{line-height:75px;margin:0 10px}nav.navigation{position:absolute;top:75px;left:0;bottom:0;width:300px;background-color:#282B30;color:#FAFAFA;overflow-y:auto;z-index:998}nav.navigation>ul{list-style-type:none;margin:0;padding:0}nav.navigation>ul>li.navigation-link{display:block;cursor:pointer;border-bottom:1px solid #282828;padding:10px 20px}nav.navigation>ul>li.navigation-link.active,nav.navigation>ul>li.navigation-link:active,nav.navigation>ul>li.navigation-link:hover{background-color:#2E3136}    </style></head><body><header class=\"fixed-header\"><h1>TheBox v</h1></header><nav class=\"navigation\"><ul><li class=\"navigation-link\" data-scene-name=\"status\">Status</li><li class=\"navigation-link\" data-scene-name=\"server/list\">Serveurs</li></ul></nav><div class=\"container\" id=\"scene-container\"></div><script src=\"https://code.jquery.com/jquery-3.1.1.min.js\"></script><script>function createError(a,e){var n=$('<div class=\"error-container\"></div>');return n.append('<span class=\"material-icons\">'+a+\"</span>\"),n.append(\"<p>\"+e+\"</p>\"),n}$(\"li.navigation-link\").click(function(){var a=$(\"div#scene-container\"),e=$(this).data(\"scene-name\");a.html('<span class=\"loader\" style=\"margin: 0 auto;\"></span>'),$(\"li.navigation-link.active\").each(function(){$(this).removeClass(\"active\")}),$(this).addClass(\"active\"),$.ajax({dataType:\"json\",url:\"/api/\"+e,error:function(e,n,r){a.html(createError(\"&#xE000;\",\"Une erreur est survenue pendant le chargement de la page (code d'erreur : \"+e.status+\")\"))},success:function(n,r,i){var t=\"<hr><p>Response from <b>/api/\"+e+\"</b></p>\";t+=\"<ul>\",$.each(n,function(a,e){t+=\"<li>\"+a+\": \"+e}),t+=\"</ul>\",a.html(t)}})});</script></body></html>";

	}

}