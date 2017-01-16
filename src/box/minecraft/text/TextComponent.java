package box.minecraft.text;

import java.util.ArrayList;
import java.util.List;

import net.sociuris.json.JsonArray;
import net.sociuris.json.JsonElement;
import net.sociuris.json.JsonObject;

public abstract class TextComponent {
	
	private TextComponent parent = null;
	private TextColor color = TextColor.WHITE;
	private Boolean bold = false;
	private Boolean italic = false;
	private Boolean underlined = false;
	private Boolean strikethrough = false;
	private Boolean obfuscated = false;
	private List<TextComponent> extra = new ArrayList<TextComponent>();

	public TextColor getColor() {
		if (color != null)
			return color;
		else {
			if (parent != null)
				return parent.getColor();
			else
				return TextColor.WHITE;
		}
	}

	public TextComponent setColor(TextColor color) {
		this.color = color;
		return this;
	}

	public boolean isBold() {
		if(bold != null)
			return bold.booleanValue();
		else
			return (parent != null) && (parent.isBold());
	}

	public TextComponent setBold(Boolean bold) {
		this.bold = bold;
		return this;
	}

	public boolean isItalic() {
		if (italic != null)
			return italic.booleanValue();
		else
			return (parent != null) && (parent.isItalic());
	}

	public TextComponent setItalic(Boolean italic) {
		this.italic = italic;
		return this;
	}

	public boolean isUnderlined() {
		if (underlined != null)
			return underlined.booleanValue();
		else
			return (parent != null) && (parent.isUnderlined());
	}

	public TextComponent setUnderlined(Boolean underlined) {
		this.underlined = underlined;
		return this;
	}

	public boolean isStrikethrough() {
		if (strikethrough != null)
			return strikethrough.booleanValue();
		else
			return (parent != null) && (parent.isStrikethrough());
	}

	public TextComponent setStrikethrough(Boolean strikethrough) {
		this.strikethrough = strikethrough;
		return this;
	}

	public boolean isObfuscated() {
		if (obfuscated != null)
			return obfuscated.booleanValue();
		else
			return (parent != null) && (parent.isObfuscated());
	}

	public TextComponent setObfuscated(Boolean obfuscated) {
		this.obfuscated = obfuscated;
		return this;
	}

	public TextComponent addExtra(String text) {
		this.addExtra(new TextComponentString(text));
		return this;
	}

	public TextComponent addExtra(TextComponent component) {
		component.parent = this;
		this.extra.add(component);
		return this;
	}

	public TextComponent setExtra(List<TextComponent> components) {
		for (TextComponent component : components)
			component.parent = this;
		this.extra = components;
		return this;
	}

	public List<TextComponent> getExtra() {
		return extra;
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName() + "(color=" + getColor() + ", bold=" + this.bold + ", italic="
				+ this.italic + ", underlined=" + this.underlined + ", strikethrough=" + this.strikethrough
				+ ", obfuscated=" + this.obfuscated + ", extra=[size="
				+ String.valueOf(extra.size()) + "])";
	}

	public abstract String toPrintableMessage();

	public abstract JsonObject toJsonObject();
	
	public TextComponentArray toArray() {
		return new TextComponentArray(this);
	}

	protected void setData(JsonObject jsonObj) {
		jsonObj.addProperty("color", this.getColor().name().toLowerCase());
		jsonObj.addProperty("bold", this.isBold());
		jsonObj.addProperty("italic", this.isItalic());
		jsonObj.addProperty("underlined", this.isUnderlined());
		jsonObj.addProperty("strikethrough", this.isStrikethrough());
		jsonObj.addProperty("obfuscated", this.isObfuscated());
		if (!extra.isEmpty()) {
			JsonArray extraArray = new JsonArray();
			for (TextComponent textComponent : extra)
				extraArray.addProperty(textComponent.toJsonObject());
			jsonObj.addProperty("extra", extraArray);
		}
	}

	protected void fromJson(JsonObject jsonObj) {
		if (jsonObj.hasValue("color"))
			this.setColor(TextColor.valueOf(jsonObj.get("color").getAsString().toUpperCase()));
		if (jsonObj.hasValue("bold"))
			this.setBold(jsonObj.get("bold").getAsBoolean());
		if (jsonObj.hasValue("italic"))
			this.setItalic(jsonObj.get("italic").getAsBoolean());
		if (jsonObj.hasValue("underlined"))
			this.setUnderlined(jsonObj.get("underlined").getAsBoolean());
		if (jsonObj.hasValue("strikethrough"))
			this.setStrikethrough(jsonObj.get("strikethrough").getAsBoolean());
		if (jsonObj.hasValue("obfuscated"))
			this.setObfuscated(jsonObj.get("obfuscated").getAsBoolean());
		if (jsonObj.hasValue("extra")) {
			for (JsonElement jsonElement : jsonObj.get("extra").getAsJsonArray()) {
				if (jsonElement.isJsonObject())
					this.addExtra(TextComponentUtils.createTextComponentFromJson(jsonElement.getAsJsonObject()));
				else if (jsonElement.isJsonPrimitive())
					this.addExtra(jsonElement.getAsString());
			}
		}
	}

}