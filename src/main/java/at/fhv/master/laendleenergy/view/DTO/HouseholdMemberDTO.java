package at.fhv.master.laendleenergy.view.DTO;

import at.fhv.master.laendleenergy.domain.HouseholdMember;

public class HouseholdMemberDTO {
    private String name;
    private int numberOfCreatedTags;

    public HouseholdMemberDTO() {
    }

    public HouseholdMemberDTO(String name, int numberOfCreatedTags) {
        this.name = name;
        this.numberOfCreatedTags = numberOfCreatedTags;
    }

    public static HouseholdMemberDTO create(HouseholdMember householdMember) {
        return new HouseholdMemberDTO(householdMember.getName(), householdMember.getNumberOfCreatedTags());
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
}
