package facades;

import com.avaje.ebean.Ebean;
import dto.AccountDTO;
import entities.Account;
import entities.Bank;

import javax.inject.Inject;

public class AccountsFacade {
    @Inject
    private UserDataFacade userDataFacade;

    public void deleteAccountDTO(AccountDTO accountDTO) {
        Bank bank = Ebean.getDefaultServer().createQuery(Bank.class).where()
                .eq("bik", accountDTO.getBik()).and().ieq("fullName", accountDTO.getName()).findUnique();

        Account acc = Ebean.getDefaultServer().createQuery(Account.class)
                .where()
                .eq("bank", bank)
                .and()
                .ieq("account", accountDTO.getAcc())
                .and()
                .eq("userData", userDataFacade.getUserData())
                .findUnique();
        deleteAccount(acc);
    }


    public void deleteAccount(Account account) {
        account.delete();
    }

    public void saveAccDTO(AccountDTO accDto) {
        Bank bank = Ebean.getDefaultServer().createQuery(Bank.class)
                .where()
                .eq("bik", accDto.getBik())
                .and()
                .ieq("fullName", accDto.getName())
                .findUnique();
        Account acc = new Account();
        acc.setBank(bank);
        acc.setUserData(userDataFacade.getUserData());
        acc.setAccount(accDto.getAcc());
        acc.save();
    }
}
