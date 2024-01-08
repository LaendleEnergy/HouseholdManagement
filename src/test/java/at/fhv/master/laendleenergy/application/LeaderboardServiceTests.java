package at.fhv.master.laendleenergy.application;

import at.fhv.master.laendleenergy.domain.EnergySavingTarget;
import at.fhv.master.laendleenergy.domain.Household;
import at.fhv.master.laendleenergy.domain.HouseholdMember;
import at.fhv.master.laendleenergy.domain.Incentive;
import at.fhv.master.laendleenergy.domain.exceptions.HouseholdNotFoundException;
import at.fhv.master.laendleenergy.persistence.HouseholdRepository;
import at.fhv.master.laendleenergy.view.DTO.HouseholdMemberDTO;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.LinkedList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
@Transactional
public class LeaderboardServiceTests {
    @InjectMock
    HouseholdRepository repository;
    @Inject
    LeaderboardService service;
    static Household household;
    static final String householdId = "householdId1";

    @BeforeEach
    void setUp() throws HouseholdNotFoundException {
        household = new Household(householdId,
                new Incentive(),
                new EnergySavingTarget(),
                new LinkedList<>(),
                new LinkedList<>());

        List<HouseholdMember> members = new LinkedList<>();
        members.add(new HouseholdMember("testemail1@email.com", 3, household));
        members.add(new HouseholdMember("testemail2@email.com", 10, household));
        members.add(new HouseholdMember("testemail3@email.com", 1, household));
        household.setHouseholdMembers(members);

        Mockito.when(repository.getMembersOfHousehold(householdId)).thenReturn(household.getHouseholdMembers());
    }

    @Test
    public void getLeaderboardOfHouseholdTest() throws HouseholdNotFoundException {
        List<HouseholdMemberDTO> members = service.getLeaderboardOfHousehold(householdId);

        List<HouseholdMemberDTO> expectedLeaderboard = new LinkedList<>();
        expectedLeaderboard.add(HouseholdMemberDTO.create(household.getHouseholdMembers().get(2)));
        expectedLeaderboard.add(HouseholdMemberDTO.create(household.getHouseholdMembers().get(0)));
        expectedLeaderboard.add(HouseholdMemberDTO.create(household.getHouseholdMembers().get(1)));

        assertEquals(expectedLeaderboard.get(0).getEmailAddress(), members.get(0).getEmailAddress());
        assertEquals(expectedLeaderboard.get(1).getEmailAddress(), members.get(1).getEmailAddress());
        assertEquals(expectedLeaderboard.get(2).getEmailAddress(), members.get(2).getEmailAddress());
    }
}
