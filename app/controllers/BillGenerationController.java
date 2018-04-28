package controllers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.openhtmltopdf.DOMBuilder;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import dto.BillDTO;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import play.mvc.Controller;
import play.mvc.Result;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Type;

public class BillGenerationController extends Controller {

    public Result generatePdf() {

        String textBill = request().body().asText();
        Type billDTOType = new TypeToken<BillDTO>() {
        }.getType();
        BillDTO billDTO = new Gson().fromJson(textBill, billDTOType);
        PdfRendererBuilder builder = new PdfRendererBuilder();
        ByteArrayOutputStream fileStream = new ByteArrayOutputStream();
        Document doc = Jsoup.parse("<style>" +
                "@font-face {\n" +
                "  font-family: 'Arial';\n" +
                "  /* Note: No trailing format tag is allowed. */\n" +
                "  src: url(file:C:/Windows/fonts/Arial.ttf);\n" +
                "  font-weight: normal;\n" +
                "  font-style: normal;\n" +
                "}" +
                "</style>" +
                "<table style=\"font-family:Arial; width:100%;\">\n" +
                "            <tbody id=\"uslug_table_body\">\n" +
                "            <tr id=\"error_row\" style=\"display:none; color:red;\"><td colspan=\"7\"><div>Заполните значения в подсвеченных ячейках!</div></td></tr>\n" +
                "            <tr id=\"error_val_row\" style=\"display:none; color:red;\"><td colspan=\"7\"><div>Завершите редактирование данных в таблице!</div></td></tr>\n" +
                "            <tr>\n" +
                "                <td><label>№</label></td>\n" +
                "                <td><label>Код</label></td>\n" +
                "                <td><label>Наименование товара/работ/услуг</label></td>\n" +
                "                <td><label>Кол-во</label></td>\n" +
                "                <td><label>Цена</label></td>\n" +
                "                <td><label>Сумма</label></td>\n" +
                "                <td><a style=\"padding: 1px; cursor:pointer;\" class=\"add_button\" onclick=\"addRow(event)\"></a></td>\n" +
                "            </tr>\n" +
                "            <tr class=\"filled_uslugs\" data-status=\"valid\">\n" +
                "                <td data-value=\"num\"><div>1</div></td>\n" +
                "                <td data-value=\"code\"><div data-value=\"code\">123</div></td>\n" +
                "                <td data-value=\"type\"><div data-value=\"type\">123</div></td>\n" +
                "                <td data-value=\"count\"><div data-value=\"count\">123</div></td>\n" +
                "                <td data-value=\"price\"><div data-value=\"price\">1123</div></td>\n" +
                "                <td data-value=\"summ\"><div>138129</div></td>\n" +
                "                <td><a style=\"padding: 1px; cursor:pointer;\" class=\"edit_button\" onclick=\"edit(event)\"></a></td>\n" +
                "                <td><a class=\"remove_button\" style=\"padding: 1px;\" onclick=\"removeRow(event)\"></a></td>\n" +
                "            </tr>\n" +
                "            </tbody>\n" +
                "        </table>");
        org.w3c.dom.Document doc1 = DOMBuilder.jsoup2DOM(doc);
        builder.withW3cDocument(doc1, "");
        builder.toStream(fileStream);
        try {
            builder.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
        response().setHeader("Content-Type", "application/pdf");
        return ok(fileStream.toByteArray());
    }
}
