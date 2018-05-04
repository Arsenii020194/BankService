package html;

import com.google.gson.Gson;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import dto.AccountDTO;
import dto.QrDTO;
import dto.inetshop.BillDTO;
import dto.inetshop.UslugDTO;
import entities.Account;
import entities.UserData;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static html.HtmlUtils.LINE_BREAK;

public class UserBillToHtmlConverter {

    private BillDTO billDTO;
    private UserData userData;

    public UserBillToHtmlConverter(BillDTO billDTO, UserData userData) {
        this.billDTO = billDTO;
        this.userData = userData;
    }

    public String convert() {
        StringBuilder document = new StringBuilder();
        document.append(makeAgreement());
        document.append(LINE_BREAK);
        document.append(makeConsumerTable());
        document.append(LINE_BREAK);
        document.append(makeBankTable());
        document.append(LINE_BREAK);
        document.append(makeCustumerRecieverTable());
        document.append(LINE_BREAK);
        document.append(makeUslugTable());
        document.append(LINE_BREAK);
        document.append(makeFooterTable());
        document.append(LINE_BREAK);
        document.append(generateQrImg());


        return document.toString();
    }

    private String makeCustumerRecieverTable() {
        HtmlTable customerRecieverTable = new HtmlTable();

        String nowDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.YYYY"));
        HtmlCell hat = new HtmlCell("Счет на оплату №" + billDTO.getBillNumber() + " от " + nowDate, "colspan=2");
        HtmlRow hatRow = new HtmlRow(Collections.singletonList(hat), "");

        HtmlCell recieverHatCell = new HtmlCell("Поставщик", "");
        StringBuilder fullUserDataInfo = new StringBuilder();
        fullUserDataInfo
                .append(userData.getFullName())
                .append(", ИНН: ")
                .append(userData.getInn())
                .append(", КПП: ")
                .append(userData.getKpp())
                .append(", Адрес: ")
                .append(userData.getAdress());

        HtmlCell recieverCell = new HtmlCell(fullUserDataInfo.toString(), "");
        HtmlRow recieverRow = new HtmlRow(Arrays.asList(recieverHatCell, recieverCell), "");


        HtmlCell customerHatCell = new HtmlCell("Покупатель", "");
        HtmlCell customerCell = new HtmlCell(billDTO.getCustomer(), "colspan=2");
        HtmlRow customerRow = new HtmlRow(Arrays.asList(customerHatCell, customerCell), "");

        customerRecieverTable.addRow(hatRow);
        customerRecieverTable.addRow(recieverRow);
        customerRecieverTable.addRow(customerRow);

        return customerRecieverTable.asText();
    }

    private String makeUslugTable() {
        HtmlTable uslugTable = new HtmlTable();

        List<UslugDTO> uslugs = billDTO.getUslugs();
        HtmlCell num = new HtmlCell("№", "");
        HtmlCell code = new HtmlCell("Код", "");
        HtmlCell usl = new HtmlCell("Товары/Работы/Услуги", "");
        HtmlCell count = new HtmlCell("Кол-во", "");
        HtmlCell price = new HtmlCell("Цена", "");
        HtmlCell summ = new HtmlCell("Сумма", "");

        HtmlRow hatRow = new HtmlRow(Arrays.asList(num, code, usl, count, price, summ), "");
        uslugTable.addRow(hatRow);

        for (UslugDTO uslugDTO : uslugs) {
            HtmlCell numVal = new HtmlCell(String.valueOf(uslugDTO.getNum()), "");
            HtmlCell codeVal = new HtmlCell(String.valueOf(uslugDTO.getCode()), "");
            HtmlCell uslVal = new HtmlCell(uslugDTO.getType(), "");
            HtmlCell countVal = new HtmlCell(String.valueOf(uslugDTO.getCount()), "");
            HtmlCell priceVal = new HtmlCell(String.format("%.2f", (uslugDTO.getPrice())), "");
            HtmlCell summVal = new HtmlCell(String.format("%.2f", (uslugDTO.getSumm())), "");
            HtmlRow row = new HtmlRow(Arrays.asList(numVal, codeVal, uslVal, countVal, priceVal, summVal), "");
            uslugTable.addRow(row);
        }
        return uslugTable.asText();
    }

    private String makeBankTable() {

        HtmlCell consumer = new HtmlCell("Счета", "colspan=3");
        HtmlRow hat = new HtmlRow(Collections.singletonList(consumer), "");

        HtmlCell name = new HtmlCell("Название", "");
        HtmlCell bik = new HtmlCell("БИК", "");
        HtmlCell account = new HtmlCell("Счет", "");

        HtmlRow bankDataColNames = new HtmlRow(Arrays.asList(name, bik, account), "");

        HtmlTable table = new HtmlTable();
        table.addRow(hat);
        table.addRow(bankDataColNames);
        List<Account> accounts = userData.getAccounts();

        for (int i = 0; i < accounts.size(); i++) {
            Account acc = accounts.get(i);
            HtmlCell nameVal = new HtmlCell(acc.getBank().getFullName(), "");
            HtmlCell bikVal = new HtmlCell(acc.getBank().getBik().toString(), "");
            HtmlCell accVal = new HtmlCell(acc.getAccount(), "");
            HtmlRow bankDataColValues = new HtmlRow(Arrays.asList(nameVal, bikVal, accVal), "");
            table.addRow(bankDataColValues);
        }
        return table.asText();
    }

    private String makeConsumerTable() {
        HtmlCell consumer = new HtmlCell("Получатель", "colspan=4");
        HtmlRow hat = new HtmlRow(Collections.singletonList(consumer), "");

        HtmlCell name = new HtmlCell("Название организации", "");
        HtmlCell inn = new HtmlCell("ИНН", "");
        HtmlCell kpp = new HtmlCell("КПП", "");

        HtmlRow userDataColNames = new HtmlRow(Arrays.asList(name, inn, kpp), "");

        HtmlCell nameVal = new HtmlCell(userData.getFullName(), "");
        HtmlCell innVal = new HtmlCell(userData.getInn().toString(), "");
        HtmlCell kppVal = new HtmlCell(userData.getKpp().toString(), "");

        HtmlRow userDataColValues = new HtmlRow(Arrays.asList(nameVal, innVal, kppVal), "");

        HtmlTable table = new HtmlTable();

        table.addRow(hat);
        table.addRow(userDataColNames);
        table.addRow(userDataColValues);

        return table.asText();
    }

    private String makeAgreement() {
        String toDate = LocalDate.now().plusDays(userData.getBillProlongation()).format(DateTimeFormatter.ofPattern("dd.MM.YYYY"));
        HtmlCell cell = new HtmlCell("Внимание! счет действителен до " + toDate + ". Оплата данного счета означает согласие с условиями поставки товара. " +
                "Уведомление об оплате обязательно, в противном случае не гарантируется наличие товара на складе. Товар отпускается по факту прихода денег на р/с Поставщика, " +
                "самовывозом при наличии доверенности и паспорта.");
        HtmlRow row = new HtmlRow(Collections.singletonList(cell), "");
        HtmlTable table = new HtmlTable(Collections.singletonList(row), "");

        return table.asText();
    }

    private String makeFooterTable() {
        HtmlCell leader = new HtmlCell("Руководитель", "");
        HtmlCell leaderSign = new HtmlCell("", "class=\"underlined_cell\"");
        HtmlCell leaderSignDesc = new HtmlCell("", "class=\"underlined_cell\"");

        HtmlCell accountant = new HtmlCell("Бухгалтер", "");
        HtmlCell accountantSign = new HtmlCell("", "class=\"underlined_cell\"");
        HtmlCell accountantSignDesc = new HtmlCell("", "class=\"underlined_cell\"");

        HtmlCell manager = new HtmlCell("Менеджер", "");
        HtmlCell managerSign = new HtmlCell("", "class=\"underlined_cell\"");
        HtmlCell managerSignDesc = new HtmlCell("", "class=\"underlined_cell\"");

        HtmlCell sign = new HtmlCell("(подпись)", "class=\"footer_cell\"");
        HtmlCell description = new HtmlCell("(расшифровка)", "class=\"footer_cell\"");
        HtmlCell emptyCell = new HtmlCell("", "");

        HtmlRow helperRow = new HtmlRow(Arrays.asList(emptyCell, sign, description), "");
        HtmlRow leaderRow = new HtmlRow(Arrays.asList(leader, leaderSign, leaderSignDesc), "");
        HtmlRow accountantRow = new HtmlRow(Arrays.asList(accountant, accountantSign, accountantSignDesc), "");
        HtmlRow managerRow = new HtmlRow(Arrays.asList(manager, managerSign, managerSignDesc), "");

        HtmlTable footerTable = new HtmlTable();
        footerTable.setStyle("");

        footerTable.addRow(leaderRow);
        footerTable.addRow(helperRow);
        footerTable.addRow(accountantRow);
        footerTable.addRow(helperRow);
        footerTable.addRow(managerRow);
        footerTable.addRow(helperRow);

        return footerTable.asText();
    }

    private String generateQrImg() {
        QrDTO dto = new QrDTO();
        dto.setInn(userData.getInn());
        dto.setKpp(userData.getKpp());
        dto.setNameOrg(userData.getFullName());
        dto.setSumm(billDTO.getFinalSum());
        List<AccountDTO> accounts = userData.getAccounts().stream().map(f -> {
            AccountDTO accountDTO = new AccountDTO();
            accountDTO.setAcc(f.getAccount());
            accountDTO.setBik(f.getBank().getBik());
            accountDTO.setName(f.getBank().getFullName());
            return accountDTO;
        }).collect(Collectors.toList());
        dto.setAccounts(accounts);
        dto.setType(billDTO.getBillDest());
        QRCodeWriter qrCodeWriter = new QRCodeWriter();

        Hashtable hints = new Hashtable();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        BitMatrix bitMatrix = null;
        try {
            bitMatrix = qrCodeWriter.encode(
                    new Gson().toJson(dto),
                    BarcodeFormat.QR_CODE,
                    200, 200, hints);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", out);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "<img style=\"border:1px solid black;\" src=\"data:image/png;base64," + Base64.getEncoder().encodeToString(out.toByteArray()) + "\"></img>";
    }
}
