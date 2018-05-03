package html;

import java.util.ArrayList;
import java.util.List;

import static html.HtmlUtils.*;

public class HtmlTable implements HtmlTextable {
    private List<HtmlRow> rows = new ArrayList<>();
    private String style = "style=\"border-spacing: 0em 0em;\" class=\"bordered\"";

    public HtmlTable() {
    }

    public HtmlTable(List<HtmlRow> rows, String style) {
        this.rows = rows;
        this.style = style;
    }

    public HtmlTable(List<HtmlRow> rows) {
        this.rows = rows;
    }


    public void addRow(HtmlRow row) {
        rows.add(row);
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    @Override
    public String asText() {
        StringBuilder tableBuilder = new StringBuilder();
        for (int i = 0; i < rows.size(); i++) {
            tableBuilder.append(rows.get(i).asText());
        }

        return TABLE_PATTERN
                .replace(CONTENT_PATTERN, tableBuilder)
                .replace(STYLE_PATTERN, style);
    }
}
