package at.fhv.master.laendleenergy.domain;

import java.time.LocalDate;

public class EnergySavingTarget {

    private LocalDate startDate;
    private LocalDate endDate;
    private int percentage;

    public EnergySavingTarget(LocalDate startDate, LocalDate endDate, int percentage) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.percentage = percentage;
    }

    public EnergySavingTarget() {
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public int getPercentage() {
        return percentage;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }
}
