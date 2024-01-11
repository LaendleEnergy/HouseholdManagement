package at.fhv.master.laendleenergy.application;

import at.fhv.master.laendleenergy.domain.EnergySavingTarget;
import at.fhv.master.laendleenergy.domain.Household;
import at.fhv.master.laendleenergy.domain.Incentive;
import at.fhv.master.laendleenergy.domain.exceptions.HouseholdNotFoundException;
import at.fhv.master.laendleenergy.persistence.EnergySavingRepository;
import at.fhv.master.laendleenergy.view.DTO.IncentiveDTO;
import at.fhv.master.laendleenergy.view.DTO.SavingTargetDTO;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

@QuarkusTest
@Transactional
public class EnergyServiceTests {

    @InjectMock
    EnergySavingRepository repository;
    @Inject
    EnergySavingService service;
    static Household household;
    static final String householdId = "householdId1";

    @BeforeEach
    void setUp() throws HouseholdNotFoundException {
        household = new Household(householdId,
                new Incentive("Pizza", LocalDate.of(2050, 10, 31)),
                new EnergySavingTarget(10, "Vormonat"),
                new LinkedList<>(),
                new LinkedList<>());

        Mockito.when(repository.getCurrentSavingTarget(householdId)).thenReturn(household.getSavingTarget());
        Mockito.when(repository.getCurrentIncentive(householdId)).thenReturn(household.getIncentive());
    }

    @Test
    public void getCurrentSavingTargetTest() throws HouseholdNotFoundException {
        SavingTargetDTO savingTargetDTO = service.getCurrentSavingTarget(householdId);
        assertEquals(household.getSavingTarget().getPercentage(), savingTargetDTO.getPercentage());
        assertEquals(household.getSavingTarget().getTimeframe(), savingTargetDTO.getTimeframe());
        assertEquals(household.getSavingTarget().getId(), savingTargetDTO.getId());
    }

    @Test
    public void updateSavingTargetTest() throws HouseholdNotFoundException {
        SavingTargetDTO savingTargetDTO = SavingTargetDTO.create(household.getSavingTarget());
        savingTargetDTO.setPercentage(20);
        savingTargetDTO.setTimeframe("Vorjahr");

        service.updateSavingTarget(householdId, savingTargetDTO);

        Mockito.verify(repository, times(1)).updateSavingTarget(anyString(), any());
    }

    @Test
    public void getCurrentIncentiveTest() throws HouseholdNotFoundException {
        IncentiveDTO incentiveDTO = service.getCurrentIncentive(householdId);
        assertEquals(household.getIncentive().getDescription(), incentiveDTO.getDescription());
        assertEquals(household.getIncentive().getEndDate().toString(), incentiveDTO.getEndDate());
        assertEquals(household.getIncentive().getId(), incentiveDTO.getId());
    }

    @Test
    public void updateIncentiveTest() throws HouseholdNotFoundException {
        IncentiveDTO incentiveDTO = IncentiveDTO.create(household.getIncentive());
        incentiveDTO.setDescription("new description");
        incentiveDTO.setEndDate(LocalDate.of(2000, 10, 1).toString());

        service.updateIncentive(householdId, incentiveDTO);

        Mockito.verify(repository, times(1)).updateIncentive(anyString(), any());
    }
}
