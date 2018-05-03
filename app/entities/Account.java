package entities;

import com.avaje.ebean.Model;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.Objects;

@Entity
@Table(name = "accounts")
public class Account extends Model {

    @Column(name = "id_account")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private BigInteger id;

    @Column
    @ManyToOne
    @JoinColumn(name = "user_data")
    private UserData userData;

    @Column
    @ManyToOne
    @JoinColumn(name = "bank")
    private Bank bank;

    @Column
    private String account;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public UserData getUserData() {
        return userData;
    }

    public void setUserData(UserData userData) {
        this.userData = userData;
    }

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account1 = (Account) o;
        return Objects.equals(userData, account1.userData) &&
                Objects.equals(bank, account1.bank) &&
                Objects.equals(account, account1.account);
    }

    @Override
    public int hashCode() {

        return Objects.hash(userData, bank, account);
    }
}
