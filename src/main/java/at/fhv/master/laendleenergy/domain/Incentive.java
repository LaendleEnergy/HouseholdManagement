package at.fhv.master.laendleenergy.domain;

public class Incentive {
    private String textDescription;

    public Incentive(String textDescription) {
        this.textDescription = textDescription;
    }

    public Incentive() {}

    public String getTextDescription() {
        return textDescription;
    }

    public void setTextDescription(String textDescription) {
        this.textDescription = textDescription;
    }
}
