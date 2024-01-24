package at.fhv.master.laendleenergy.unit;

import at.fhv.master.laendleenergy.domain.*;
import at.fhv.master.laendleenergy.domain.events.*;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.UUID;

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

        household.addMemberToHousehold(new HouseholdMember("member1", "name", 0, household));
    }

    @Test
    public void deviceTest() {
        DeviceCategory deviceCategory = new DeviceCategory("Mikrowelle");
        Device device = new Device(new DeviceCategory("Kuehlschrank"), "Kuehlschrank1", household);
        device.setDeviceCategory(deviceCategory);
        device.setName("Kuehlschrank2");
        Household newHousehold = new Household();
        device.setHousehold(newHousehold);

        assertEquals(deviceCategory, device.getDeviceCategory());
        assertEquals("Kuehlschrank2", device.getName());
        assertEquals(newHousehold, device.getHousehold());
    }

    @Test
    public void deviceCategoryTest() {
        String deviceCategoryName = "name";
        DeviceCategory deviceCategory = new DeviceCategory(deviceCategoryName);
        assertEquals(deviceCategoryName, deviceCategory.getCategoryName());
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
    public void householdCreateTest() {
        Household newhousehold = Household.create("household1");
        assertEquals("household1", newhousehold.getId());
        assertEquals(0, newhousehold.getHouseholdMembers().size());
        assertEquals(0, newhousehold.getDevices().size());
    }

    @Test
    public void householdMemberTest() {
        HouseholdMember householdMember = new HouseholdMember("1", "test@email.com", 0, household);
        householdMember.setName("test@email.com.new");
        householdMember.setNumberOfCreatedTags(1);
        householdMember.setId("2");
        Household newHousehold = new Household();
        householdMember.setHousehold(newHousehold);

        assertEquals("test@email.com.new", householdMember.getName());
        assertEquals(newHousehold, householdMember.getHousehold());
        assertEquals(1, householdMember.getNumberOfCreatedTags());
        assertEquals("2", householdMember.getId());
    }

    @Test
    public void householdAddMemberTest() {
        HouseholdMember householdMember = new HouseholdMember("1", "test@email.com", 0, household);
        Household household = new Household("householdid", new Incentive(), new EnergySavingTarget(), new LinkedList<>(), new LinkedList<>());
        assertEquals(0, household.getHouseholdMembers().size());

        household.addMemberToHousehold(householdMember);
        assertEquals(1, household.getHouseholdMembers().size());
    }

    @Test
    public void householdMemberCreateTest() {
        MemberAddedEvent event = new MemberAddedEvent("event1", "member1", "name", "household1", LocalDateTime.now());

        HouseholdMember actual = HouseholdMember.create(event.getMemberId(), event.getName(), household);

        assertEquals(event.getMemberId(), actual.getId());
        assertEquals(household.getId(), actual.getHousehold().getId());
        assertEquals(event.getName(), actual.getName());
        assertEquals(0, actual.getNumberOfCreatedTags());
    }

    @Test
    public void householdMemberUpdateTest() {
        String oldName = "oldname";
        String newName = "newname";

        HouseholdMember householdMember = new HouseholdMember("1", oldName, 0, household);
        Household household = new Household("householdid", new Incentive(), new EnergySavingTarget(), new LinkedList<>(), new LinkedList<>());
        assertEquals(0, household.getHouseholdMembers().size());

        household.addMemberToHousehold(householdMember);
        assertEquals(oldName, household.getHouseholdMembers().get(0).getName());

        household.updateMember(householdMember.getId(), newName);
        assertEquals(newName, household.getHouseholdMembers().get(0).getName());
    }

    @Test
    public void householdRemoveMemberTest() {
        MemberRemovedEvent event = new MemberRemovedEvent("event1", "member1", "household1", LocalDateTime.now());

        assertEquals(1, household.getHouseholdMembers().size());

        household.removeMember(event.getMemberId());

        assertEquals(0, household.getHouseholdMembers().size());
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
        event.setTimestamp(LocalDateTime.of(2000,1,1, 1, 1, 1));
        event.setMemberId("user1");
        event.setHouseholdId("household1");

        assertEquals("event2", event.getEventId());
        assertEquals("d2", event.getDeviceId());
        assertEquals(LocalDateTime.of(2000,1,1, 1, 1, 1), event.getTimestamp());
        assertEquals("user1", event.getMemberId());
        assertEquals("household1", event.getHouseholdId());
    }

    @Test
    public void memberAddedEventTest() {
        MemberAddedEvent event = new MemberAddedEvent("event1", "member1", "name", "household1", LocalDateTime.now());
        event.setEventId("event2");
        event.setName("name");
        event.setTimestamp(LocalDateTime.of(2000,1,1, 1, 1, 1));
        event.setMemberId("user1");
        event.setHouseholdId("household1");

        assertEquals("event2", event.getEventId());
        assertEquals("name", event.getName());
        assertEquals(LocalDateTime.of(2000,1,1, 1, 1, 1), event.getTimestamp());
        assertEquals("user1", event.getMemberId());
        assertEquals("household1", event.getHouseholdId());
    }

    @Test
    public void memberUpdatedEventTest() {
        MemberUpdatedEvent event = new MemberUpdatedEvent("event1", "member1", "name", "household1", LocalDateTime.now());
        event.setEventId("event2");
        event.setName("name");
        event.setTimestamp(LocalDateTime.of(2000,1,1, 1, 1, 1));
        event.setMemberId("user1");
        event.setHouseholdId("household1");

        assertEquals("event2", event.getEventId());
        assertEquals("name", event.getName());
        assertEquals(LocalDateTime.of(2000,1,1, 1, 1, 1), event.getTimestamp());
        assertEquals("user1", event.getMemberId());
        assertEquals("household1", event.getHouseholdId());
    }

    @Test
    public void memberRemovedEventTest() {
        MemberRemovedEvent event = new MemberRemovedEvent("event1", "member1", "household1", LocalDateTime.now());
        event.setEventId("event2");
        event.setTimestamp(LocalDateTime.of(2000,1,1, 1, 1, 1));
        event.setMemberId("user1");
        event.setHouseholdId("household1");

        assertEquals("event2", event.getEventId());
        assertEquals(LocalDateTime.of(2000,1,1, 1, 1, 1), event.getTimestamp());
        assertEquals("user1", event.getMemberId());
        assertEquals("household1", event.getHouseholdId());
    }

    @Test
    public void householdCreatedEventTest() {
        HouseholdCreatedEvent event = new HouseholdCreatedEvent("event1", "member1", "name", "householdId", LocalDateTime.now());
        event.setEventId("event2");
        event.setTimestamp(LocalDateTime.of(2000,1,1, 1, 1, 1));
        event.setMemberId("user1");
        event.setName("namenew");
        event.setHouseholdId("household1");

        assertEquals("event2", event.getEventId());
        assertEquals(LocalDateTime.of(2000,1,1, 1, 1, 1), event.getTimestamp());
        assertEquals("user1", event.getMemberId());
        assertEquals("household1", event.getHouseholdId());
        assertEquals("namenew", event.getName());
    }

    @Test
    public void deviceAddedEventTest() {
        DeviceAddedEvent event = new DeviceAddedEvent("event1", "d1", "m1", "name1", "h1", "c1", LocalDateTime.of(2000,1,1,1,1));
        event.setName("name2");
        event.setCategoryName("c2");

        assertEquals("name2", event.getName());
        assertEquals("c2", event.getCategoryName());
    }

    @Test
    public void deviceCategoryAddedEventTest() {
        DeviceCategoryAddedEvent event = new DeviceCategoryAddedEvent("event1", "d1", "m1", "name1", "h1");
        event.setName("name2");

        assertEquals("name2", event.getName());
        assertEquals("d1", event.getDeviceId());
    }

    @Test
    public void deviceRemovedEventTest() {
        DeviceRemovedEvent event = new DeviceRemovedEvent(UUID.randomUUID().toString(), "m1", "name", "h1", LocalDateTime.now(), "c1");
        event.setDeviceName("name2");
        event.setDeviceCategoryName("c2");

        assertEquals("name2", event.getDeviceName());
        assertEquals("c2", event.getDeviceCategoryName());
    }

}
