package dto;

import java.util.List;

public class BillDTO {

    private int numOrder;
    private int billNumber;
    private String customer;
    private List<UslugDTO> uslugs;
    private double finalSum;

    public int getNumOrder() {
        return numOrder;
    }

    public void setNumOrder(int numOrder) {
        this.numOrder = numOrder;
    }

    public int getBillNumber() {
        return billNumber;
    }

    public void setBillNumber(int billNumber) {
        this.billNumber = billNumber;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public List<UslugDTO> getUslugs() {
        return uslugs;
    }

    public void setUslugs(List<UslugDTO> uslugs) {
        this.uslugs = uslugs;
    }

    public double getFinalSum() {
        return finalSum;
    }
}
