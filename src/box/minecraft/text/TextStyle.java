package box.minecraft.text;

public enum TextStyle {

	OBFUSCATED('k'),
	BOLD('l'),
	STRIKETHROUGH('m'),
	UNDERLINE('n'),
	ITALIC('o'),
	RESET('r');

	private final char formattingCode;

	private TextStyle(char formattingCode) {
		this.formattingCode = formattingCode;
	}

	public char getFormattingCode() {
		return formattingCode;
	}
	
	@Override
	public String toString() {
		return "\u00A7" + this.formattingCode;
	}

}
