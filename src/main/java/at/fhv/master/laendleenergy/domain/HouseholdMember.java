package at.fhv.master.laendleenergy.domain;

import jakarta.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name="household_member")
public class HouseholdMember {
    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "emailAddress")
    private String emailAddress;
    @Column(name = "numberOfCreatedTags")
    private int numberOfCreatedTags;
    @ManyToOne
    @JoinColumn(name = "household_id")
    private Household household;

    public HouseholdMember() {}

    public HouseholdMember(String id, String emailAddress, int numberOfCreatedTags, Household household) {
        this.id = id;
        this.emailAddress = emailAddress;
        this.numberOfCreatedTags = numberOfCreatedTags;
        this.household = household;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
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
