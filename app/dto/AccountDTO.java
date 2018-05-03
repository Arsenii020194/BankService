package dto;

public class AccountDTO {

    private Integer bik;
    private String name;
    private String acc;

    public Integer getBik() {
        return bik;
    }

    public String getName() {
        return name;
    }

    public String getAcc() {
        return acc;
    }

    public void setBik(Integer bik) {
        this.bik = bik;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAcc(String acc) {
        this.acc = acc;
    }
}
