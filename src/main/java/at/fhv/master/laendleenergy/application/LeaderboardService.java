package at.fhv.master.laendleenergy.application;

import at.fhv.master.laendleenergy.domain.exceptions.HouseholdNotFoundException;
import at.fhv.master.laendleenergy.view.DTO.HouseholdMemberDTO;
import java.util.List;

public interface LeaderboardService {
    List<HouseholdMemberDTO> getLeaderboardOfHousehold(String householdId) throws HouseholdNotFoundException;
}
