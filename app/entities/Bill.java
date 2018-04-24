package entities;

import com.avaje.ebean.Model;

import javax.persistence.*;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "bill")
public class Bill extends Model {

    @Column(name = "id_bill")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private BigInteger id;

    @Column
    private Timestamp date;

    @Column
    private Timestamp activeFor;

    @Column
    @ManyToOne
    @JoinColumn(name = "reciever")
    private User reciever;

    @Column
    private String customer;

    @Transient
    private List<String> uslugs;

    @Column
    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] file;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public Timestamp getActiveFor() {
        return activeFor;
    }

    public void setActiveFor(Timestamp activeFor) {
        this.activeFor = activeFor;
    }

    public User getReciever() {
        return reciever;
    }

    public void setReciever(User reciever) {
        this.reciever = reciever;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public List<String> getUslugs() {
        return uslugs;
    }

    public void setUslugs(List<String> uslugs) {
        this.uslugs = uslugs;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bill bill = (Bill) o;
        return Objects.equals(date, bill.date) &&
                Objects.equals(activeFor, bill.activeFor) &&
                Objects.equals(reciever, bill.reciever) &&
                Objects.equals(customer, bill.customer) &&
                Arrays.equals(file, bill.file);
    }

    @Override
    public int hashCode() {

        int result = Objects.hash(date, activeFor, reciever, customer);
        result = 31 * result + Arrays.hashCode(file);
        return result;
    }
}

