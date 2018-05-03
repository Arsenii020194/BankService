package facades;

import com.avaje.ebean.Ebean;
import entities.Bank;

import java.util.List;

public class BankFacade {

    public List<Bank> getBanks() {
        List<Bank> banks = Ebean.getDefaultServer().createQuery(Bank.class).findList();
        return banks;
    }
}
