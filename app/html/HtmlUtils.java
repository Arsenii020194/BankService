package html;

public class HtmlUtils {
    public static final String BORDER_STYLE_TABLE = "style=\"border:1px solid black;\"";
    public static final String LINE_BREAK = "<br></br>";
    public static final String CONTENT_PATTERN = "***content***";
    public static final String STYLE_PATTERN = "***style***";
    public static final String CELL_PATTERN = "<td " + STYLE_PATTERN + ">" + CONTENT_PATTERN + "</td>";
    public static final String ROW_PATTERN = "<tr " + STYLE_PATTERN + ">" + CONTENT_PATTERN + "</tr>";
    public static final String TABLE_PATTERN = "<table " + STYLE_PATTERN + "><tbody>" + CONTENT_PATTERN + "</tbody></table>";

}
