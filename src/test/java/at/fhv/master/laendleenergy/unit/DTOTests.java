package at.fhv.master.laendleenergy.unit;

import at.fhv.master.laendleenergy.domain.*;
import at.fhv.master.laendleenergy.view.DTO.HouseholdMemberDTO;
import at.fhv.master.laendleenergy.view.DTO.IncentiveDTO;
import at.fhv.master.laendleenergy.view.DTO.SavingTargetDTO;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.LinkedList;
import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
public class DTOTests {

    static Household household;
    @BeforeEach
    void setUp() {
        household = new Household("householdId1",
                new Incentive("Pizza", LocalDate.of(2050, 10, 31)),
                new EnergySavingTarget(10, "Vormonat"),
                new LinkedList<>(),
                new LinkedList<>());
    }

    @Test
    public void energySavingTargetDTOTest() {
        SavingTargetDTO savingTargetDTO = new SavingTargetDTO("1", 10, "Vorjahr");
        savingTargetDTO.setTimeframe("Vormonat");
        savingTargetDTO.setPercentage(40);
        savingTargetDTO.setId("2");

        assertEquals(40, savingTargetDTO.getPercentage());
        assertEquals("Vormonat", savingTargetDTO.getTimeframe());
    }

    @Test
    public void energySavingTargetDTOCreateTest() {
        SavingTargetDTO savingTargetDTO = new SavingTargetDTO("1", 10, "Vorjahr");
        EnergySavingTarget energySavingTarget = savingTargetDTO.toEnergySavingTarget();

        assertEquals(energySavingTarget.getId(), savingTargetDTO.getId());
        assertEquals(energySavingTarget.getPercentage(), savingTargetDTO.getPercentage());
        assertEquals(energySavingTarget.getTimeframe(), savingTargetDTO.getTimeframe());
    }

    @Test
    public void energySavingTargetDTOCreateTest2() {
        EnergySavingTarget energySavingTarget = new EnergySavingTarget("1", 10, "Vorjahr");
        SavingTargetDTO savingTargetDTO = SavingTargetDTO.create(energySavingTarget);

        assertEquals(energySavingTarget.getId(), savingTargetDTO.getId());
        assertEquals(energySavingTarget.getPercentage(), savingTargetDTO.getPercentage());
        assertEquals(energySavingTarget.getTimeframe(), savingTargetDTO.getTimeframe());
    }

    @Test
    public void householdMemberDTOTest() {
        HouseholdMemberDTO householdMember = new HouseholdMemberDTO("test@email.com", 0);
        householdMember.setName("test@email.com.new");
        householdMember.setNumberOfCreatedTags(1);

        assertEquals("test@email.com.new", householdMember.getName());
        assertEquals(1, householdMember.getNumberOfCreatedTags());
    }

    @Test
    public void householdMemberDTOCreateTest() {
        HouseholdMember householdMember = new HouseholdMember("1", "test@email.de", 0, household);
        HouseholdMemberDTO householdMemberDTO = HouseholdMemberDTO.create(householdMember);

        assertEquals(householdMember.getName(), householdMemberDTO.getName());
        assertEquals(householdMember.getNumberOfCreatedTags(), householdMemberDTO.getNumberOfCreatedTags());
    }

    @Test
    public void incentiveDTOTest() {
        IncentiveDTO incentive = new IncentiveDTO("1", "test description", LocalDate.of(2020, 10, 10).toString());
        incentive.setDescription("new test description");
        incentive.setEndDate(LocalDate.of(2021, 10, 10).toString());
        incentive.setId("2");

        assertEquals("2", incentive.getId());
        assertEquals("new test description", incentive.getDescription());
        assertEquals(LocalDate.of(2021, 10, 10).toString(), incentive.getEndDate());
    }

    @Test
    public void incentiveDTOCreateTest() {
        IncentiveDTO incentiveDTO = new IncentiveDTO("1", "test description", LocalDate.of(2020, 10, 10).toString());
        Incentive incentive = incentiveDTO.toIncentive();

        assertEquals(incentiveDTO.getId(), incentive.getId());
        assertEquals(incentiveDTO.getDescription(), incentive.getDescription());
        assertEquals(LocalDate.parse(incentiveDTO.getEndDate()), incentive.getEndDate());
    }

    @Test
    public void incentiveDTOCreateTest2() {
        Incentive incentive = new Incentive("1", "test description", LocalDate.of(2020, 10, 10));
        IncentiveDTO incentiveDTO = IncentiveDTO.create(incentive);

        assertEquals(incentiveDTO.getId(), incentive.getId());
        assertEquals(incentiveDTO.getDescription(), incentive.getDescription());
        assertEquals(LocalDate.parse(incentiveDTO.getEndDate()), incentive.getEndDate());
    }
}
