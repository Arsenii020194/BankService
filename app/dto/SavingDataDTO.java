package dto;

import entities.UserData;

import java.util.List;

public class SavingDataDTO {

    private List<AccountDTO> accountsToDelete;
    private List<AccountDTO> newAccounts;
    private UserData orgData;

    public List<AccountDTO> getAccountsToDelete() {
        return accountsToDelete;
    }

    public List<AccountDTO> getNewAccounts() {
        return newAccounts;
    }

    public UserData getOrgData() {
        return orgData;
    }
}
