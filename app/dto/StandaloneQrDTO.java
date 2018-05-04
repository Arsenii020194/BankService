package dto;

public class StandaloneQrDTO {
    private String login;
    private double finalSum;
    private String destination;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public double getFinalSum() {
        return finalSum;
    }

    public void setFinalSum(double finalSum) {
        this.finalSum = finalSum;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }
}
