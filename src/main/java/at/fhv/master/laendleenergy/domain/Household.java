package at.fhv.master.laendleenergy.domain;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "household")
public class Household {
    @Id
    @Column(name = "household_id")
    private String id;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "incentive_id")
    private Incentive incentive;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "saving_target_id")
    private EnergySavingTarget savingTarget;
    @OneToMany(mappedBy = "household", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Device> devices;

    public Household() {

    }

    public Household(String id, Incentive incentive, EnergySavingTarget savingTarget, List<Device> devices) {
        this.incentive = incentive;
        this.savingTarget = savingTarget;
        this.devices = devices;
        this.id = id;
    }

    public Incentive getIncentive() {
        return incentive;
    }

    public void setIncentive(Incentive incentive) {
        this.incentive = incentive;
    }

    public EnergySavingTarget getSavingTarget() {
        return savingTarget;
    }

    public void setSavingTarget(EnergySavingTarget savingTarget) {
        this.savingTarget = savingTarget;
    }

    public List<Device> getDevices() {
        return devices;
    }

    public void setDevices(List<Device> devices) {
        this.devices = devices;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
