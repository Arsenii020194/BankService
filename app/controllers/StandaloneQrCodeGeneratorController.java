package controllers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.openhtmltopdf.DOMBuilder;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import dto.StandaloneQrDTO;
import entities.UserData;
import facades.AuthFacade;
import html.QrCodeGenerator;
import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class StandaloneQrCodeGeneratorController extends Controller {
    @Inject
    private AuthFacade authFacade;

    public Result generatePdf() throws IOException {
        String textBill = request().body().asText();
        Type standaloneQrDTO = new TypeToken<StandaloneQrDTO>() {
        }.getType();
        StandaloneQrDTO qrDTO = new Gson().fromJson(textBill, standaloneQrDTO);

        UserData userData = authFacade.findByLogin(qrDTO.getLogin());
        QrCodeGenerator qrCodeGenerator = new QrCodeGenerator();
        PdfRendererBuilder builder = new PdfRendererBuilder();
        ByteArrayOutputStream fileStream = new ByteArrayOutputStream();
        File styleFile = new File("public/stylesheets/print.css");
        List<String> lines = FileUtils.readLines(styleFile, "UTF-8");
        StringBuilder styleBuilder = new StringBuilder();
        lines.forEach(styleBuilder::append);

        Document doc = Jsoup.parse(
                "<style>" +
                        styleBuilder.toString() +
                        "</style>");

        doc.body().append(qrCodeGenerator.generateQr(userData, qrDTO.getFinalSum(), qrDTO.getDestination()));

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
