package at.fhv.master.laendleenergy.unit;

import at.fhv.master.laendleenergy.domain.*;
import at.fhv.master.laendleenergy.domain.events.TaggingCreatedEvent;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedList;
import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
public class DomainTests {

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
    public void deviceTest() {
        Device device = new Device(DeviceCategory.FRIDGE, "Kühlschrank1", household);
        device.setDeviceCategory(DeviceCategory.MICROWAVE);
        device.setName("Kühlschrank2");
        Household newHousehold = new Household();
        device.setHousehold(newHousehold);

        assertEquals(DeviceCategory.MICROWAVE, device.getDeviceCategory());
        assertEquals("Kühlschrank2", device.getName());
        assertEquals(newHousehold, device.getHousehold());
    }

    @Test
    public void deviceCategoryTest() {
        assertEquals(DeviceCategory.get("Haarföhn"), DeviceCategory.HAIR_DRYER);
        assertEquals(DeviceCategory.get("Kühlschrank"), DeviceCategory.FRIDGE);
        assertEquals(DeviceCategory.get("Mikrowelle"), DeviceCategory.MICROWAVE);
    }

    @Test
    public void energySavingTargetTest() {
        EnergySavingTarget energySavingTarget = new EnergySavingTarget(30, "Vormonat");
        energySavingTarget.setPercentage(40);
        energySavingTarget.setTimeframe("Vorjahr");

        assertEquals(40, energySavingTarget.getPercentage());
        assertEquals("Vorjahr", energySavingTarget.getTimeframe());
    }

    @Test
    public void householdTest() {
        Household household1 = new Household();
        household1.setId("1");
        household1.setDevices(new LinkedList<>());
        household1.setHouseholdMembers(new LinkedList<>());
        household1.setSavingTarget(new EnergySavingTarget());
        household1.setIncentive(new Incentive());

        assertEquals("1", household1.getId());
    }

    @Test
    public void householdMemberTest() {
        HouseholdMember householdMember = new HouseholdMember("1", "test@email.com", 0, household);
        householdMember.setEmailAddress("test@email.com.new");
        householdMember.setNumberOfCreatedTags(1);
        householdMember.setId("2");
        Household newHousehold = new Household();
        householdMember.setHousehold(newHousehold);

        assertEquals("test@email.com.new", householdMember.getEmailAddress());
        assertEquals(newHousehold, householdMember.getHousehold());
        assertEquals(1, householdMember.getNumberOfCreatedTags());
        assertEquals("2", householdMember.getId());
    }

    @Test
    public void incentiveTest() {
        Incentive incentive = new Incentive("test description", LocalDate.of(2020, 10, 10));
        incentive.setDescription("new test description");
        incentive.setEndDate(LocalDate.of(2021, 10, 10));

        assertEquals("new test description", incentive.getDescription());
        assertEquals(LocalDate.of(2021, 10, 10), incentive.getEndDate());
    }

    @Test
    public void taggingCreatedEventTest() {
        TaggingCreatedEvent event = new TaggingCreatedEvent("event1", LocalDateTime.now(), "1", "D1", "h1");
        event.setEventId("event2");
        event.setDeviceId("d2");
        event.setTaggingTime(LocalDateTime.of(2000,1,1, 1, 1, 1));
        event.setUserId("user1");
        event.setHouseholdId("household1");

        assertEquals("event2", event.getEventId());
        assertEquals("d2", event.getDeviceId());
        assertEquals(LocalDateTime.of(2000,1,1, 1, 1, 1), event.getTaggingTime());
        assertEquals("user1", event.getUserId());
        assertEquals("household1", event.getHouseholdId());
    }
}
