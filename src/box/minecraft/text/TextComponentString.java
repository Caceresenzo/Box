package box.minecraft.text;

import com.google.gson.JsonObject;

public class TextComponentString extends TextComponent {

	public static final TextComponentString EMPTY = new TextComponentString("");
	
	private final String text;

	public TextComponentString(String text) {
		if(text != null)
			this.text = text;
		else
			throw new IllegalArgumentException("text is null");
	}

	public String getText() {
		return text;
	}

	@Override
	public JsonObject toJsonObject() {
		JsonObject jsonObj = new JsonObject();
		jsonObj.addProperty("text", text);
		super.setData(jsonObj);
		return jsonObj;
	}

	@Override
	public String toPrintableMessage() {
		return text;
	}

}