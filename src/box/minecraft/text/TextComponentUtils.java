package box.minecraft.text;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class TextComponentUtils {

	private TextComponentUtils() {
	}

	public static TextComponent createTextComponentFromJson(JsonObject jsonObj) {
		if (jsonObj.has("text")) {
			TextComponentString textComponentString = new TextComponentString(jsonObj.get("text").getAsString());
			textComponentString.fromJson(jsonObj);
			return textComponentString;
		} else if (jsonObj.has("translate")) {
			TextComponentTranslation textComponentTranslate;
			if (!jsonObj.has("with"))
				textComponentTranslate = new TextComponentTranslation(jsonObj.get("translate").getAsString());
			else {
				Iterator<JsonElement> iterator = jsonObj.get("with").getAsJsonArray().iterator();
				List<JsonElement> jsonElementList = new ArrayList<JsonElement>();
				while (iterator.hasNext())
					jsonElementList.add(iterator.next());
				textComponentTranslate = new TextComponentTranslation(jsonObj.get("translate").getAsString(),
						jsonElementList.toArray(new JsonElement[jsonElementList.size()]));
			}
			textComponentTranslate.fromJson(jsonObj);
			return textComponentTranslate;
		} else
			return null;
	}

}