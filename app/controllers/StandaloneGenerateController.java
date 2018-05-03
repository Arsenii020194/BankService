package controllers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.openhtmltopdf.DOMBuilder;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import dto.BillDTO;
import dto.StandaloneBillDTO;
import entities.Bill;
import entities.UserData;
import facades.AuthFacade;
import facades.BillsFacade;
import html.UserBillToHtmlConverter;
import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class StandaloneGenerateController extends Controller {
    @Inject
    private AuthFacade authFacade;

    @Inject
    private BillsFacade billsFacade;

    public Result generatePdf() throws TransformerException, IOException {
        String textBill = request().body().asText();
        Type standaloneBillDTO = new TypeToken<StandaloneBillDTO>() {
        }.getType();
        StandaloneBillDTO billDTO = new Gson().fromJson(textBill, standaloneBillDTO);
        UserData userData = authFacade.findByLogin(billDTO.getLogin());
        UserBillToHtmlConverter userBillToHtmlConverter = new UserBillToHtmlConverter(billDTO.getBillDTO(), userData);
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

        doc.body().append(userBillToHtmlConverter.convert());

        org.w3c.dom.Document doc1 = DOMBuilder.jsoup2DOM(doc);

        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();

        transformer.transform(new DOMSource(doc1), new StreamResult(System.out));

        builder.withW3cDocument(doc1, "");
        builder.toStream(fileStream);
        try {
            builder.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
        response().setHeader("Content-Type", "application/pdf");
        saveBill(billDTO.getBillDTO(), fileStream.toByteArray(), billDTO.getLogin());
        return ok(fileStream.toByteArray());
    }

    private void saveBill(BillDTO billDTO, byte[] file, String login) {
        Bill bill = new Bill();
        UserData userData = authFacade.findByLogin(login);
        Timestamp nowDate = Timestamp.valueOf(LocalDate.now().atStartOfDay());
        Timestamp activeFor = Timestamp.valueOf(LocalDate.now().plusDays(userData.getBillProlongation()).atStartOfDay());
        bill.setDate(nowDate);
        bill.setActiveFor(activeFor);
        bill.setCustomer(billDTO.getCustomer());
        bill.setNum(Integer.valueOf(billDTO.getBillNumber()));
        bill.setReciever(userData.getUser());
        bill.setNum(Integer.valueOf(billDTO.getNumOrder()));
        bill.setUslugs(billDTO.getUslugs().stream().map(f -> new Gson().toJson(f)).collect(Collectors.toList()));
        bill.setFile(file);
        bill.save();
    }
}
