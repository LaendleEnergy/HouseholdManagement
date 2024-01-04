package at.fhv.master.laendleenergy.persistence;

import at.fhv.master.laendleenergy.domain.EnergySavingTarget;
import at.fhv.master.laendleenergy.domain.Household;
import at.fhv.master.laendleenergy.domain.Incentive;
import at.fhv.master.laendleenergy.domain.exceptions.HouseholdNotFoundException;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.time.LocalDate;
import java.util.LinkedList;
import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
@Transactional
public class EnergySavingRepositoryTests {
    @Inject
    EnergySavingRepository repository;
    @InjectMock
    HouseholdRepository householdRepository;
    static Household household;
    static final String householdId = "householdId1";

    @BeforeEach
    void setUp() throws HouseholdNotFoundException {
        household = new Household(householdId,
                new Incentive("Pizza", LocalDate.of(2050, 10, 31)),
                new EnergySavingTarget(10, "Vormonat"),
                new LinkedList<>(),
                new LinkedList<>());

        Mockito.when(householdRepository.getHouseholdById(householdId)).thenReturn(household);
        Mockito.when(householdRepository.createHouseholdIfNotExists(householdId)).thenReturn(household);
    }

    @Test
    public void getCurrentSavingTargetTest() {
        EnergySavingTarget savingTarget = repository.getCurrentSavingTarget(householdId);
        assertEquals(household.getSavingTarget().getPercentage(), savingTarget.getPercentage());
        assertEquals(household.getSavingTarget().getTimeframe(), savingTarget.getTimeframe());
        assertEquals(household.getSavingTarget().getId(), savingTarget.getId());
    }

    @Test
    public void updateSavingTargetTest() {
        EnergySavingTarget savingTarget = household.getSavingTarget();
        savingTarget.setPercentage(20);
        savingTarget.setTimeframe("Vorjahr");

        repository.updateSavingTarget(householdId, savingTarget);

        assertEquals(household.getSavingTarget(), savingTarget);
    }

    @Test
    public void getCurrentIncentiveTest() {
        Incentive incentive = repository.getCurrentIncentive(householdId);
        assertEquals(household.getIncentive().getDescription(), incentive.getDescription());
        assertEquals(household.getIncentive().getEndDate(), incentive.getEndDate());
        assertEquals(household.getIncentive().getId(), incentive.getId());
    }

    @Test
    public void updateIncentiveTest() {
        Incentive incentive = household.getIncentive();
        incentive.setDescription("new description");
        incentive.setEndDate(LocalDate.of(2000, 10, 1));

        repository.updateIncentive(householdId, incentive);

        assertEquals(household.getIncentive(), incentive);
    }
}
