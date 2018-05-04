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
import entities.UserData;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Hashtable;
import java.util.List;
import java.util.stream.Collectors;

public class QrCodeGenerator {

    public String generateQr(UserData userData, double finalsum, String destination) {
        QrDTO dto = new QrDTO();
        dto.setInn(userData.getInn());
        dto.setKpp(userData.getKpp());
        dto.setNameOrg(userData.getFullName());
        dto.setSumm(finalsum);
        List<AccountDTO> accounts = userData.getAccounts().stream().map(f -> {
            AccountDTO accountDTO = new AccountDTO();
            accountDTO.setAcc(f.getAccount());
            accountDTO.setBik(f.getBank().getBik());
            accountDTO.setName(f.getBank().getFullName());
            return accountDTO;
        }).collect(Collectors.toList());
        dto.setAccounts(accounts);
        dto.setType(destination);
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

    public String generateCreditQr(UserData userData, List<AccountDTO> accountDTOS, double finalsum, String destination) {
        QrDTO dto = new QrDTO();
        dto.setInn(userData.getInn());
        dto.setKpp(userData.getKpp());
        dto.setNameOrg(userData.getFullName());
        dto.setSumm(finalsum);
        dto.setAccounts(accountDTOS);
        dto.setType(destination);
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

