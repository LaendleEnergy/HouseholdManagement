package at.fhv.master.laendleenergy.domain;

import jakarta.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name="energy_saving_target")
public class EnergySavingTarget {
    @Id
    @Column(name = "saving_target_id")
    private String id;
    @Column(name = "timeframe")
    private String timeframe;
    @Column(name = "percentage")
    private int percentage;

    public EnergySavingTarget(int percentage, String timeframe) {
        this.percentage = percentage;
        this.timeframe = timeframe;
        this.id = UUID.randomUUID().toString();
    }

    public EnergySavingTarget() {
        this.id = UUID.randomUUID().toString();
    }

    public EnergySavingTarget(String id, int percentage, String timeframe) {
        this.id = id;
        this.timeframe = timeframe;
        this.percentage = percentage;
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
