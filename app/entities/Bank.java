package entities;

import com.avaje.ebean.Model;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "bank")
public class Bank extends Model{
    @Column(name = "id_bank")
    @Id
    @GeneratedValue
    private BigInteger id;
    @Column(name = "full_name")
    private String fullName;
    @Column
    private Integer bik;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "bank")
    private List<Account> account;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Integer getBik() {
        return bik;
    }

    public void setBik(Integer bik) {
        this.bik = bik;
    }

    public List<Account> getAccount() {
        return account;
    }

    public void setAccount(List<Account> account) {
        this.account = account;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bank bank = (Bank) o;
        return Objects.equals(fullName, bank.fullName) &&
                Objects.equals(bik, bank.bik) &&
                Objects.equals(account, bank.account);
    }

    @Override
    public int hashCode() {

        return Objects.hash(fullName, bik, account);
    }
}
