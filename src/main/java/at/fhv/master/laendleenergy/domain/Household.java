package at.fhv.master.laendleenergy.domain;

import jakarta.persistence.*;

import java.util.LinkedList;
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
    @OneToMany(mappedBy = "household", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HouseholdMember> householdMembers;

    public Household() {

    }

    public Household(String id, Incentive incentive, EnergySavingTarget savingTarget, List<Device> devices, List<HouseholdMember> householdMembers) {
        this.incentive = incentive;
        this.savingTarget = savingTarget;
        this.devices = devices;
        this.id = id;
        this.householdMembers = householdMembers;
    }

    public static Household create(String householdId) {
        return new Household(householdId, new Incentive("Noch keine Belohnung festgelegt.", null), new EnergySavingTarget(), new LinkedList<>(), new LinkedList<>());
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

    public void setHouseholdMembers(List<HouseholdMember> householdMembers) {
        this.householdMembers = householdMembers;
    }

    public List<HouseholdMember> getHouseholdMembers() {
        return householdMembers;
    }

    public void addMemberToHousehold(HouseholdMember m) {
        householdMembers.add(m);
    }
    public void removeMember(String memberId) {
        householdMembers.removeIf(m -> m.getId().equals(memberId));
    }
}
