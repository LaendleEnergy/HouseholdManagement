package at.fhv.master.laendleenergy.view.DTO;

import at.fhv.master.laendleenergy.domain.Household;
import at.fhv.master.laendleenergy.domain.HouseholdMember;

public class HouseholdMemberDTO {
    private String emailAddress;
    private int numberOfCreatedTags;

    public HouseholdMemberDTO() {
    }

    public HouseholdMemberDTO(String emailAddress, int numberOfCreatedTags) {
        this.emailAddress = emailAddress;
        this.numberOfCreatedTags = numberOfCreatedTags;
    }

    public static HouseholdMemberDTO create(HouseholdMember householdMember) {
        return new HouseholdMemberDTO(householdMember.getEmailAddress(), householdMember.getNumberOfCreatedTags());
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
}
