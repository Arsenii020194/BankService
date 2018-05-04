package dto.credit;

import dto.AccountDTO;
import dto.StandaloneQrDTO;

public class QrCreditDTO {
    private AccountDTO account;
    private StandaloneQrDTO qrDTO;

    public AccountDTO getAccount() {
        return account;
    }

    public void setAccount(AccountDTO account) {
        this.account = account;
    }

    public StandaloneQrDTO getQrDTO() {
        return qrDTO;
    }

    public void setQrDTO(StandaloneQrDTO qrDTO) {
        this.qrDTO = qrDTO;
    }
}
