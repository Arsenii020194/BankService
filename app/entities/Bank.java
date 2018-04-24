package entities;

import com.avaje.ebean.Model;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.Objects;

@Entity
@Table(name = "bank")
public class Bank extends Model{
    @Column(name = "id_bank")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private BigInteger id;
    @Column(name = "full_name")
    private String fullName;
    @Column
    private Integer bik;

    @OneToOne(mappedBy = "bank")
    private UserData userData ;

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

    public UserData getUserData() {
        return userData;
    }

    public void setUserData(UserData userData) {
        this.userData = userData;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bank bank = (Bank) o;
        return Objects.equals(fullName, bank.fullName) &&
                Objects.equals(bik, bank.bik) &&
                Objects.equals(userData, bank.userData);
    }

    @Override
    public int hashCode() {

        return Objects.hash(fullName, bik, userData);
    }
}
