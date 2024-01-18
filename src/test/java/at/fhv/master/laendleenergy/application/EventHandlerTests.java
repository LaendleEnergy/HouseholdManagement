package at.fhv.master.laendleenergy.application;

import at.fhv.master.laendleenergy.application.streams.EventHandler;
import at.fhv.master.laendleenergy.domain.*;
import at.fhv.master.laendleenergy.domain.events.*;
import at.fhv.master.laendleenergy.domain.exceptions.HouseholdNotFoundException;
import at.fhv.master.laendleenergy.persistence.DeviceRepository;
import at.fhv.master.laendleenergy.persistence.HouseholdRepository;
import io.quarkus.test.InjectMock;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

@QuarkusTest
@TestTransaction
public class EventHandlerTests {

    @InjectMock
    HouseholdRepository householdRepository;
    @InjectMock
    DeviceRepository deviceRepository;
    @Inject
    EventHandler eventHandler;

    static Household household;
    static final String householdId = "h1";
    static final String memberId = "m1";

    @BeforeEach
    void setUp() throws HouseholdNotFoundException {
        household = new Household(householdId,
                new Incentive("Pizza", LocalDate.of(2050, 10, 31)),
                new EnergySavingTarget(10, "Vormonat"),
                new LinkedList<>(),
                new LinkedList<>());

        List<HouseholdMember> members = new LinkedList<>();
        members.add(new HouseholdMember(memberId, "testemail1@email.com", 3, household));
        members.add(new HouseholdMember("2", "testemail2@email.com", 10, household));
        members.add(new HouseholdMember("3", "testemail3@email.com", 1, household));
        household.setHouseholdMembers(members);

        Mockito.when(householdRepository.getHouseholdById(householdId)).thenReturn(household);
    }

    @Test
    public void handleHouseholdCreatedEvent() throws HouseholdNotFoundException {
        String newHouseholdId = "newHouseholdId";
        Mockito.when(householdRepository.getHouseholdById(newHouseholdId)).thenReturn(household);

        HouseholdCreatedEvent event = new HouseholdCreatedEvent("event1", memberId, "name", newHouseholdId, LocalDateTime.of(1900,1,1,1,1));
        eventHandler.handleHouseholdCreatedEvent(event);

        Mockito.verify(householdRepository, times(1)).addHousehold(any());
    }

    @Test
    public void handleMemberAddedEvent() throws HouseholdNotFoundException {
        assertEquals(3, household.getHouseholdMembers().size());

        MemberAddedEvent event = new MemberAddedEvent("event1", "member1", "name", householdId, LocalDateTime.of(1900,1,1,1,1));
        eventHandler.handleMemberAddedEvent(event);

        assertEquals(4, household.getHouseholdMembers().size());

        Mockito.verify(householdRepository, times(1)).updateHousehold(any());
    }

    @Test
    public void handleMemberRemovedEvent() throws HouseholdNotFoundException {
        assertEquals(3, household.getHouseholdMembers().size());

        MemberRemovedEvent event = new MemberRemovedEvent("event1", memberId, householdId, LocalDateTime.now());
        eventHandler.handleMemberRemovedEvent(event);

        Mockito.verify(householdRepository, times(1)).updateHousehold(any());
        assertEquals(2, household.getHouseholdMembers().size());
    }

    @Test
    public void testHandleTaggingCreatedEvent() throws HouseholdNotFoundException {
        TaggingCreatedEvent event = new TaggingCreatedEvent("e1", LocalDateTime.now(), memberId, "d1", householdId);
        HouseholdMember member = household.getHouseholdMembers().get(0);

        assertEquals(3, member.getNumberOfCreatedTags());
        eventHandler.handleTaggingCreatedEvent(event);

        assertEquals(4, member.getNumberOfCreatedTags());
    }

    @Test
    public void testHandleDeviceCategoryAddedEvent() {
        DeviceCategoryAddedEvent event = new DeviceCategoryAddedEvent("d1", "m1", "name1","h1");
        eventHandler.handleDeviceCategoryAddedEvent(event);

        Mockito.verify(deviceRepository, times(1)).addDeviceCategory(any());
    }
}

