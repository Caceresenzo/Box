package box.util;

public class TableUtils {

	public static final char ANGLE_BORDER_CHAR = '+';
	public static final char HORITONZAL_BORDER_CHAR = '-';
	public static final char VERTICAL_BORDER_CHAR = '|';

	private TableUtils() {
	}

	public static String createTable(String[] columnsTitle, String[][] rows) {
		StringBuilder builder = new StringBuilder();
		String[][] columnsValues = TableUtils.rowToColumnValues(rows);

		// Gets the width of all columns (based on the largest value/title)
		int[] columnsWidth = new int[columnsTitle.length];
		for (int i = 0; i < columnsTitle.length; i++) {
			columnsWidth[i] = StringUtils.getBiggestStrings(columnsValues[i])[0].length();
			int columnTitleLength = columnsTitle[i].length();
			if (columnTitleLength > columnsWidth[i])
				columnsWidth[i] = columnTitleLength;
		}

		String horizontalLine = String.valueOf(TableUtils.ANGLE_BORDER_CHAR);
		for (int maxWidth : columnsWidth) {
			for (int i = -1; i <= maxWidth; i++)
				horizontalLine += TableUtils.HORITONZAL_BORDER_CHAR;
			horizontalLine += TableUtils.ANGLE_BORDER_CHAR;
		}

		final String lineSeparator = System.lineSeparator();

		builder.append(horizontalLine).append(lineSeparator);
		builder.append(TableUtils.arrayToRow(columnsTitle, columnsWidth)).append(lineSeparator);
		builder.append(horizontalLine).append(lineSeparator);

		if (columnsValues.length > 0)
			for (int i = 0; i < columnsValues.length; i++)
				builder.append(TableUtils.arrayToRow(rows[i], columnsWidth)).append(lineSeparator);
		else
			builder.append("No row found.").append(lineSeparator);
		builder.append(horizontalLine);

		return builder.toString();

	}

	public static String[][] rowToColumnValues(String[][] rows) {
		// columns [ column index ] [ values array ]
		String[][] columns = new String[rows[0].length][rows.length];

		for (int i = 0; i < rows.length; i++) {
			String[] row = rows[i];
			for (int j = 0; j < row.length; j++)
				columns[j][i] = row[j];
		}
		return columns;
	}

	private static String arrayToRow(String[] values, int[] columnsWidth) {
		StringBuilder builder = new StringBuilder();
		builder.append('|');
		for (int i = 0; i < values.length; i++) {
			String value = values[i];
			builder.append(' ').append(value);
			for (int j = value.length(); j <= columnsWidth[i]; j++)
				builder.append(' ');
			builder.append('|');
		}
		return builder.toString();
	}

}