package at.fhv.master.laendleenergy.streams;

import at.fhv.master.laendleenergy.domain.EnergySavingTarget;
import at.fhv.master.laendleenergy.domain.Household;
import at.fhv.master.laendleenergy.domain.HouseholdMember;
import at.fhv.master.laendleenergy.domain.Incentive;
import at.fhv.master.laendleenergy.domain.events.TaggingCreatedEvent;
import at.fhv.master.laendleenergy.domain.exceptions.HouseholdNotFoundException;
import at.fhv.master.laendleenergy.persistence.HouseholdRepository;
import at.fhv.master.laendleenergy.streams.TaggingCreatedEventConsumer;
import io.quarkus.test.InjectMock;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import io.lettuce.core.*;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;

@QuarkusTest
@TestTransaction
public class TaggingCreatedEventConsumerTests {
    /*@Inject
    TaggingCreatedEventConsumer consumer;
    @InjectMock
    HouseholdRepository householdRepository;

    @ConfigProperty(name = "redis-host")  private String redisHost;
    @ConfigProperty(name = "redis-port")  private String redisPort;
    @ConfigProperty(name = "redis-tagging-created-key")  private String KEY;

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

        Mockito.when(householdRepository.getHouseholdById(anyString())).thenReturn(household);
        Mockito.when(householdRepository.getMembersOfHousehold(anyString())).thenReturn(household.getHouseholdMembers());
    }
    @Test
    public void testConnection() throws HouseholdNotFoundException {
        RedisClient redisClient = RedisClient.create("redis://" + redisHost + ":" + redisPort);
        StatefulRedisConnection<String, String> connection = redisClient.connect();

        try (connection) {
            RedisCommands<String, String> syncCommands = connection.sync();
            TaggingCreatedEvent event = new TaggingCreatedEvent("event1", LocalDateTime.now(), memberId, "D1", householdId);

            Map<String, String> messageBody = new HashMap<>();
            messageBody.put("eventId", event.getEventId());
            messageBody.put("taggingTime", event.getTaggingTime().toString());
            messageBody.put("userId", event.getUserId());
            messageBody.put("deviceId", event.getDeviceId());
            messageBody.put("householdId", event.getHouseholdId());

            syncCommands.xadd(KEY, messageBody);
        } finally {
            redisClient.shutdown();
        }

        consumer.consume();
    }

    // ToDo: Fails when all tests are run
    @Test
    public void testIncreaseNumberOfTagsForMember() throws HouseholdNotFoundException {
        HouseholdMember member = household.getHouseholdMembers().get(0);
        assertEquals(3, member.getNumberOfCreatedTags());

        consumer.increaseNumberOfTagsForMember(householdId, member.getId());

        assertEquals(4, member.getNumberOfCreatedTags());
    }*/
}
