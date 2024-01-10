package at.fhv.master.laendleenergy.persistence;

import at.fhv.master.laendleenergy.domain.EnergySavingTarget;
import at.fhv.master.laendleenergy.domain.Household;
import at.fhv.master.laendleenergy.domain.HouseholdMember;
import at.fhv.master.laendleenergy.domain.Incentive;
import at.fhv.master.laendleenergy.domain.exceptions.HouseholdNotFoundException;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;

@QuarkusTest
@Transactional
public class HouseholdRepositoryTests {
    @Inject
    HouseholdRepository householdRepository;
    @InjectMock
    EntityManager entityManager;

    static Household household;
    static final String householdId = "householdId1";

    @BeforeEach
    void setUp() {
        household = new Household(householdId,
                new Incentive("Pizza", LocalDate.of(2050, 10, 31)),
                new EnergySavingTarget(10, "Vormonat"),
                new LinkedList<>(),
                new LinkedList<>());
    }

    @Test
    public void createHouseholdIfNotExistsTest() {
        Household newHousehold = householdRepository.createHouseholdIfNotExists(householdId);
        assertEquals(newHousehold.getIncentive().getDescription(), "Noch keine Belohnung festgelegt.");
        assertEquals(newHousehold.getId(), householdId);
    }

    @Test
    public void createHouseholdIfNotExistsTest_HouseholdExists() {
        Mockito.when(entityManager.find(Household.class, householdId)).thenReturn(household);

        Household actualHousehold = householdRepository.createHouseholdIfNotExists(householdId);
        assertEquals(actualHousehold, household);
    }


    @Test
    public void addHouseholdTest() {
        householdRepository.addHousehold(household);

        Mockito.verify(entityManager, times(1)).persist(household);
    }

    @Test
    public void deleteHouseholdTest() throws HouseholdNotFoundException {
        Mockito.when(entityManager.find(Household.class, householdId)).thenReturn(household);

        householdRepository.deleteHousehold(householdId);

        Mockito.verify(entityManager, times(1)).remove(household);
    }

    @Test
    public void deleteHouseholdTestException() {
        assertThrows(HouseholdNotFoundException.class, () -> householdRepository.deleteHousehold(householdId));
        Mockito.verify(entityManager, times(0)).remove(household);
    }

    @Test
    public void updateHouseholdTest() throws HouseholdNotFoundException {
        Mockito.when(entityManager.find(Household.class, householdId)).thenReturn(household);

        Household updatedHousehold = household;
        updatedHousehold.setIncentive(new Incentive("new description", LocalDate.of(2000, 1,1)));
        updatedHousehold.setSavingTarget(new EnergySavingTarget(40, "Vorjahr"));
        List<HouseholdMember> members = new LinkedList<>();
        members.add(new HouseholdMember("1", "newemail@com", 0, updatedHousehold));
        updatedHousehold.setHouseholdMembers(members);
        updatedHousehold.setDevices(new LinkedList<>());

        householdRepository.updateHousehold(updatedHousehold);

        Mockito.verify(entityManager, times(1)).merge(updatedHousehold);
    }

    @Test
    public void updateHouseholdTest_HouseholdDoesNotExist() {
        Household updatedHousehold = household;
        updatedHousehold.setIncentive(new Incentive("new description", LocalDate.of(2000, 1,1)));
        updatedHousehold.setSavingTarget(new EnergySavingTarget(40, "Vorjahr"));
        List<HouseholdMember> members = new LinkedList<>();
        members.add(new HouseholdMember("1", "newemail@com", 0, updatedHousehold));
        updatedHousehold.setHouseholdMembers(members);
        updatedHousehold.setDevices(new LinkedList<>());

        assertThrows(HouseholdNotFoundException.class, () -> householdRepository.updateHousehold(updatedHousehold));
        Mockito.verify(entityManager, times(0)).merge(updatedHousehold);
    }

    @Test
    public void getHouseholdById() throws HouseholdNotFoundException {
        Mockito.when(entityManager.find(Household.class, householdId)).thenReturn(household);

        Household actualHousehold = householdRepository.getHouseholdById(householdId);
        assertEquals(actualHousehold, household);
    }

    @Test
    public void getHouseholdById_HouseholdDoesNotExist() {
        assertThrows(HouseholdNotFoundException.class, () -> householdRepository.getHouseholdById(householdId));
    }

    @Test
    public void getMembersOfHousehold() throws HouseholdNotFoundException {
        Mockito.when(entityManager.find(Household.class, householdId)).thenReturn(household);

        Household actualHousehold = householdRepository.getHouseholdById(householdId);
        assertEquals(actualHousehold.getHouseholdMembers(), household.getHouseholdMembers());
    }

    @Test
    public void getMembersOfHousehold_HouseholdDoesNotExist() {
        assertThrows(HouseholdNotFoundException.class, () -> householdRepository.getMembersOfHousehold(householdId));
    }
}
