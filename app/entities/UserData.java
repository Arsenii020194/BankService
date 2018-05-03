package entities;

import com.avaje.ebean.Model;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "user_data")
public class UserData extends Model {

    @Column(name = "id_user_data")
    @Id
    @GeneratedValue
    private BigInteger id;

    @JoinColumn(name = "user")
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private User user;

    @Column(name = "bill_prolongation")
    private Integer billProlongation;

    @Column
    private Integer inn;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "userData")
    private List<Account> accounts;

    @Column
    private Integer kpp;

    @Column
    private String fullName;

    @Column
    private String adress;

    @Column
    private String phone;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getBillProlongation() {
        return billProlongation;
    }

    public void setBillProlongation(Integer billProlongation) {
        this.billProlongation = billProlongation;
    }

    public Integer getInn() {
        return inn;
    }

    public void setInn(Integer inn) {
        this.inn = inn;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    public Integer getKpp() {
        return kpp;
    }

    public void setKpp(Integer kpp) {
        this.kpp = kpp;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserData userData = (UserData) o;
        return Objects.equals(billProlongation, userData.billProlongation) &&
                Objects.equals(inn, userData.inn) &&
                Objects.equals(kpp, userData.kpp) &&
                Objects.equals(fullName, userData.fullName) &&
                Objects.equals(adress, userData.adress) &&
                Objects.equals(phone, userData.phone);
    }

    @Override
    public int hashCode() {

        return Objects.hash(billProlongation, inn, kpp, fullName, adress, phone);
    }
}
