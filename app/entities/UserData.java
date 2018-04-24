package entities;

import com.avaje.ebean.Model;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.Objects;

@Entity
@Table(name = "user_data")
public class UserData extends Model {

    @Column(name = "id_user_data")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private BigInteger id;

    @JoinColumn(name = "user")
    @OneToOne(fetch = FetchType.LAZY)
    private User user;

    @Column(name = "bill_prolongation")
    private Integer billProlongation;

    @Column
    private Integer inn;

    @Column
    private Integer bik;

    @Column
    private Integer kpp;

    @Column
    private String fullName;

    @JoinColumn(name = "bank")
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Bank bank;

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

    public Integer getBik() {
        return bik;
    }

    public void setBik(Integer bik) {
        this.bik = bik;
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

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserData userData = (UserData) o;
        return Objects.equals(billProlongation, userData.billProlongation) &&
                Objects.equals(inn, userData.inn) &&
                Objects.equals(bik, userData.bik) &&
                Objects.equals(kpp, userData.kpp) &&
                Objects.equals(fullName, userData.fullName) &&
                Objects.equals(bank, userData.bank) &&
                Objects.equals(adress, userData.adress) &&
                Objects.equals(phone, userData.phone);
    }

    @Override
    public int hashCode() {

        return Objects.hash(billProlongation, inn, bik, kpp, fullName, bank, adress, phone);
    }
}
