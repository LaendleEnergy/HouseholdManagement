package at.fhv.master.laendleenergy.view.DTO;

import at.fhv.master.laendleenergy.domain.EnergySavingTarget;

public class SavingTargetDTO {
    private String id;
    private int percentage;
    private String timeframe;

    public SavingTargetDTO() {}

    public SavingTargetDTO(String id, int percentage, String timeframe) {
        this.id = id;
        this.percentage = percentage;
        this.timeframe = timeframe;
    }

    public static EnergySavingTarget create(SavingTargetDTO savingTargetDTO) {
        return new EnergySavingTarget(savingTargetDTO.getId(),
                savingTargetDTO.getPercentage(),
                savingTargetDTO.getTimeframe());
    }

    public static SavingTargetDTO create(EnergySavingTarget savingTarget) {
        return new SavingTargetDTO(savingTarget.getId(),
                savingTarget.getPercentage(),
                savingTarget.getTimeframe());
    }

    public int getPercentage() {
        return percentage;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTimeframe() {
        return timeframe;
    }

    public void setTimeframe(String timeframe) {
        this.timeframe = timeframe;
    }
}
