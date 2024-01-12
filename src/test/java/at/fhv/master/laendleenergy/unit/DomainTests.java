package at.fhv.master.laendleenergy.unit;

import at.fhv.master.laendleenergy.domain.*;
import at.fhv.master.laendleenergy.domain.events.HouseholdCreatedEvent;
import at.fhv.master.laendleenergy.domain.events.MemberAddedEvent;
import at.fhv.master.laendleenergy.domain.events.MemberRemovedEvent;
import at.fhv.master.laendleenergy.domain.events.TaggingCreatedEvent;
import io.lettuce.core.StreamMessage;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

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
    public void testTaggingCreatedEventFromStreamMessage() {
        TaggingCreatedEvent expected = new TaggingCreatedEvent("event1", LocalDateTime.of(2000,1,1,1,1,1), "member1", "device1", "household1");

        Map<String, String> messageBody = new HashMap<>();
        messageBody.put("eventId", expected.getEventId());
        messageBody.put("userId", expected.getMemberId());
        messageBody.put("deviceId", expected.getDeviceId());
        messageBody.put("householdId", expected.getHouseholdId());
        messageBody.put("taggingTime", expected.getTimestamp().toString());

        StreamMessage<String, String> message = new StreamMessage<>("", "id1", messageBody);

        TaggingCreatedEvent actual = TaggingCreatedEvent.fromStreamMessage(message);
        assertEquals(expected.getHouseholdId(), actual.getHouseholdId());
        assertEquals(expected.getMemberId(), actual.getMemberId());
        assertEquals(expected.getEventId(), actual.getEventId());
        assertEquals(expected.getTimestamp(), actual.getTimestamp());
        assertEquals(expected.getDeviceId(), actual.getDeviceId());
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
    public void testMemberAddedEventFromStreamMessage() {
        MemberAddedEvent expected = new MemberAddedEvent("event1", "member1", "name", "household1", LocalDateTime.of(2000,1,1,1,1,1));

        Map<String, String> messageBody = new HashMap<>();
        messageBody.put("eventId", expected.getEventId());
        messageBody.put("memberId", expected.getMemberId());
        messageBody.put("name", expected.getName());
        messageBody.put("householdId", expected.getHouseholdId());
        messageBody.put("timestamp", expected.getTimestamp().toString());

        StreamMessage<String, String> message = new StreamMessage<>("", "id1", messageBody);

        MemberAddedEvent actual = MemberAddedEvent.fromStreamMessage(message);
        assertEquals(expected.getHouseholdId(), actual.getHouseholdId());
        assertEquals(expected.getMemberId(), actual.getMemberId());
        assertEquals(expected.getEventId(), actual.getEventId());
        assertEquals(expected.getTimestamp(), actual.getTimestamp());
        assertEquals(expected.getName(), actual.getName());
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
    public void testMemberRemovedEventFromStreamMessage() {
        MemberRemovedEvent expected = new MemberRemovedEvent("event1", "member1", "household1", LocalDateTime.of(2000,1,1,1,1));

        Map<String, String> messageBody = new HashMap<>();
        messageBody.put("eventId", expected.getEventId());
        messageBody.put("memberId", expected.getMemberId());
        messageBody.put("householdId", expected.getHouseholdId());
        messageBody.put("timestamp", expected.getTimestamp().toString());

        StreamMessage<String, String> message = new StreamMessage<>("", "id1", messageBody);

        MemberRemovedEvent actual = MemberRemovedEvent.fromStreamMessage(message);
        assertEquals(expected.getHouseholdId(), actual.getHouseholdId());
        assertEquals(expected.getMemberId(), actual.getMemberId());
        assertEquals(expected.getEventId(), actual.getEventId());
        assertEquals(expected.getTimestamp(), actual.getTimestamp());
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
    public void testHouseholdCreatedEventFromStreamMessage() {
        HouseholdCreatedEvent expected = new HouseholdCreatedEvent("event1", "member1", "name", "household1", LocalDateTime.of(2000,1,1,1,1,1));

        Map<String, String> messageBody = new HashMap<>();
        messageBody.put("eventId", expected.getEventId());
        messageBody.put("memberId", expected.getMemberId());
        messageBody.put("name", expected.getName());
        messageBody.put("householdId", expected.getHouseholdId());
        messageBody.put("timestamp", expected.getTimestamp().toString());

        StreamMessage<String, String> message = new StreamMessage<>("", "id1", messageBody);

        HouseholdCreatedEvent actual = HouseholdCreatedEvent.fromStreamMessage(message);
        assertEquals(expected.getHouseholdId(), actual.getHouseholdId());
        assertEquals(expected.getMemberId(), actual.getMemberId());
        assertEquals(expected.getEventId(), actual.getEventId());
        assertEquals(expected.getTimestamp(), actual.getTimestamp());
        assertEquals(expected.getName(), actual.getName());
    }
}
