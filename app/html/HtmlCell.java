package html;

import static html.HtmlUtils.*;

public class HtmlCell implements HtmlTextable{
    private String content = "";
    private String style = "";

    public HtmlCell(String content) {
        this.content = content;
    }

    public HtmlCell(String content, String style) {
        this.content = content;
        this.style = style;
    }

    @Override
    public String asText() {
        return CELL_PATTERN
                .replace(CONTENT_PATTERN, content)
                .replace(STYLE_PATTERN, style);
    }
}
