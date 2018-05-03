package entities;

import com.avaje.ebean.Model;

import javax.persistence.*;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "bill")
public class Bill extends Model {

    @Column(name = "id_bill")
    @Id
    @GeneratedValue
    private BigInteger id;

    @Column
    private Integer num;

    @Column
    private Integer orderNumber;

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

    @Column
    @Lob
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

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
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
        return Objects.equals(num, bill.num) &&
                Objects.equals(orderNumber, bill.orderNumber) &&
                Objects.equals(date, bill.date) &&
                Objects.equals(activeFor, bill.activeFor) &&
                Objects.equals(reciever, bill.reciever) &&
                Objects.equals(customer, bill.customer) &&
                Objects.equals(uslugs, bill.uslugs);
    }

    @Override
    public int hashCode() {

        return Objects.hash(num, orderNumber, date, activeFor, reciever, customer, uslugs);
    }
}

