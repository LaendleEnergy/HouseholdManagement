package at.fhv.master.laendleenergy.domain;

import at.fhv.master.laendleenergy.domain.events.MemberAddedEvent;
import jakarta.persistence.*;

@Entity
@Table(name="household_member")
public class HouseholdMember {
    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "name")
    private String name;
    @Column(name = "numberOfCreatedTags")
    private int numberOfCreatedTags;
    @ManyToOne
    @JoinColumn(name = "household_id")
    private Household household;

    public HouseholdMember() {}

    public HouseholdMember(String id, String name, int numberOfCreatedTags, Household household) {
        this.id = id;
        this.name = name;
        this.numberOfCreatedTags = numberOfCreatedTags;
        this.household = household;
    }

    public static HouseholdMember create(String memberId, String name, Household household) {
        return new HouseholdMember(memberId, name, 0, household);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumberOfCreatedTags() {
        return numberOfCreatedTags;
    }

    public void setNumberOfCreatedTags(int numberOfCreatedTags) {
        this.numberOfCreatedTags = numberOfCreatedTags;
    }

    public Household getHousehold() {
        return household;
    }

    public void setHousehold(Household household) {
        this.household = household;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void increaseNumberOfCreatedTags() {
        this.numberOfCreatedTags +=1;
    }
}
