package at.fhv.master.laendleenergy.application;

import at.fhv.master.laendleenergy.domain.HouseholdMember;
import at.fhv.master.laendleenergy.domain.exceptions.HouseholdNotFoundException;
import at.fhv.master.laendleenergy.persistence.HouseholdRepository;
import at.fhv.master.laendleenergy.view.DTO.HouseholdMemberDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

@ApplicationScoped
public class LeaderboardServiceImpl implements LeaderboardService {

    @Inject
    HouseholdRepository householdRepository;

    @Override
    public List<HouseholdMemberDTO> getLeaderboardOfHousehold(String householdId) throws HouseholdNotFoundException {
        List<HouseholdMember> members = householdRepository.getMembersOfHousehold(householdId);
        List<HouseholdMemberDTO> memberDTOs = new LinkedList<>();

        for (HouseholdMember m : members) {
            memberDTOs.add(HouseholdMemberDTO.create(m));
        }

        memberDTOs.sort(Comparator.comparingInt(HouseholdMemberDTO::getNumberOfCreatedTags));

        return memberDTOs;
    }
}
