package dto;

import java.util.List;

public class BillDTO {

    private String numOrder;
    private String billNumber;
    private String customer;
    private String billDest;
    private List<UslugDTO> uslugs;
    private double finalSum;

    public String getBillDest() {
        return billDest;
    }

    public void setBillDest(String billDest) {
        this.billDest = billDest;
    }

    public void setFinalSum(double finalSum) {
        this.finalSum = finalSum;
    }

    public String getNumOrder() {
        return numOrder;
    }

    public void setNumOrder(String numOrder) {
        this.numOrder = numOrder;
    }

    public String getBillNumber() {
        return billNumber;
    }

    public void setBillNumber(String billNumber) {
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
