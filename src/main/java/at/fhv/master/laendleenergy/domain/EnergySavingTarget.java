package at.fhv.master.laendleenergy.domain;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name="energy_saving_target")
public class EnergySavingTarget {
    @Id
    @Column(name = "energy_saving_target_id")
    private String id;
    @Column(name = "start_date")
    private LocalDate startDate;
    @Column(name = "end_date")
    private LocalDate endDate;
    @Column(name = "percentage")
    private int percentage;
    @OneToOne(mappedBy = "saving_target")
    private Household household;

    public EnergySavingTarget(LocalDate startDate, LocalDate endDate, int percentage) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.percentage = percentage;
        this.id = UUID.randomUUID().toString();
    }

    public EnergySavingTarget() {
        this.id = UUID.randomUUID().toString();
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Household getHousehold() {
        return household;
    }

    public void setHousehold(Household household) {
        this.household = household;
    }
}
