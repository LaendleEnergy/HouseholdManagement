package at.fhv.master.laendleenergy.domain;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name="household_member")
public class HouseholdMember {
    @Id
    @Column(name = "emailAddress")
    private String emailAddress;
    @Column(name = "numberOfCreatedTags")
    private int numberOfCreatedTags;
    @ManyToOne
    @JoinColumn(name = "household_id")
    private Household household;

    public HouseholdMember() {}

    public HouseholdMember(String emailAddress, int numberOfCreatedTags, Household household) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HouseholdMember that = (HouseholdMember) o;
        return numberOfCreatedTags == that.numberOfCreatedTags && Objects.equals(emailAddress, that.emailAddress) && Objects.equals(household, that.household);
    }

    @Override
    public int hashCode() {
        return Objects.hash(emailAddress, numberOfCreatedTags, household);
    }

    @Override
    public String toString() {
        return "HouseholdMember{" +
                "emailAddress='" + emailAddress + '\'' +
                ", numberOfCreatedTags=" + numberOfCreatedTags +
                ", household=" + household +
                '}';
    }
}
