package box.minecraft.text;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.sociuris.configuration.ConfigurationArray;
import net.sociuris.configuration.ConfigurationSection;
import net.sociuris.json.JsonArray;

public class TextComponentArray implements Iterable<TextComponent> {

	private final List<TextComponent> textComponentList;

	public TextComponentArray() {
		this.textComponentList = new ArrayList<TextComponent>();
	}

	public TextComponentArray(List<TextComponent> textComponentList) {
		this.textComponentList = textComponentList;
	}

	public TextComponentArray(TextComponent... textComponents) {
		this();
		for(TextComponent textComponent : textComponents)
			this.textComponentList.add(textComponent);
	}

	public TextComponent getTextComponent(int index) {
		return textComponentList.get(index);
	}

	public TextComponent setTextComponent(int index, TextComponent textComponent) {
		return textComponentList.set(index, textComponent);
	}
	
	public void addAllTextComponent(TextComponent... textComponents) {
		for(TextComponent textComponent : textComponents)
			textComponentList.add(textComponent);
	}

	public boolean addTextComponent(TextComponent textComponent) {
		return textComponentList.add(textComponent);
	}

	public void saveToConfigurationFile(ConfigurationArray configurationArray) {
		for (TextComponent textComponent : textComponentList)
			configurationArray.add(new ConfigurationSection(textComponent.toJsonObject()));
	}

	public TextComponent[] toArray() {
		return textComponentList.toArray(new TextComponent[textComponentList.size()]);
	}
	
	public JsonArray toJsonArray() {
		JsonArray jsonArray = new JsonArray();
		for(TextComponent textComponent : textComponentList)
			jsonArray.addProperty(textComponent.toJsonObject());
		return jsonArray;
	}

	@Override
	public Iterator<TextComponent> iterator() {
		return this.textComponentList.iterator();
	}

	@Override
	public String toString() {
		return "TextComponentArray(textComponent=[size=" + String.valueOf(textComponentList.size()) + "])";
	}

	public String toPrintableMessage() {
		StringBuilder builder = new StringBuilder();
		for(TextComponent textComponent : textComponentList)
			builder.append(textComponent.toPrintableMessage());
		return builder.toString();
	}

}