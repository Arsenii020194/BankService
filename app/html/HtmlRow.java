package html;

import java.util.ArrayList;
import java.util.List;

import static html.HtmlUtils.*;

public class HtmlRow implements HtmlTextable {
    private List<HtmlCell> cells = new ArrayList<>();
    private String style = "";

    public HtmlRow(List<HtmlCell> cells) {
        this.cells = cells;
    }

    public HtmlRow(List<HtmlCell> cells, String style) {
        this.cells = cells;
        this.style = style;
    }

    public void addCell(HtmlCell cell) {
        cells.add(cell);
    }


    @Override
    public String asText() {
        StringBuilder rowBuilder = new StringBuilder();
        for (HtmlCell cell : cells) {
            rowBuilder.append(cell.asText());
        }
        return ROW_PATTERN
                .replace(CONTENT_PATTERN, rowBuilder.toString())
                .replace(STYLE_PATTERN, style);
    }
}
