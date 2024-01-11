package at.fhv.master.laendleenergy.streams;

import at.fhv.master.laendleenergy.domain.EnergySavingTarget;
import at.fhv.master.laendleenergy.domain.Household;
import at.fhv.master.laendleenergy.domain.HouseholdMember;
import at.fhv.master.laendleenergy.domain.Incentive;
import at.fhv.master.laendleenergy.domain.events.MemberAddedEvent;
import at.fhv.master.laendleenergy.domain.exceptions.HouseholdNotFoundException;
import at.fhv.master.laendleenergy.persistence.HouseholdRepository;
import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import io.quarkus.test.InjectMock;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;

@QuarkusTest
@TestTransaction
public class MemberAddedEventConsumerTests {
    /*@Inject
    MemberAddedEventConsumer consumer;
    @InjectMock
    HouseholdRepository householdRepository;

    @ConfigProperty(name = "redis-host")  private String redisHost;
    @ConfigProperty(name = "redis-port")  private String redisPort;
    @ConfigProperty(name = "redis-member-added-key")  private String KEY;

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
    }
    @Test
    public void testConnection() throws HouseholdNotFoundException {
        RedisClient redisClient = RedisClient.create("redis://" + redisHost + ":" + redisPort);
        StatefulRedisConnection<String, String> connection = redisClient.connect();

        try (connection) {
            RedisCommands<String, String> syncCommands = connection.sync();
            MemberAddedEvent event = new MemberAddedEvent("event1", memberId, "name", householdId, LocalDateTime.now());

            Map<String, String> messageBody = new HashMap<>();
            messageBody.put("eventId", event.getEventId());
            messageBody.put("memberId", event.getMemberId());
            messageBody.put("name", event.getName());
            messageBody.put("householdId", event.getHouseholdId());
            messageBody.put("timestamp", event.getTimestamp().toString());

            syncCommands.xadd(KEY, messageBody);
        } finally {
            redisClient.shutdown();
        }

        consumer.consume();
    }

    // ToDo: Fails when all tests are run
    @Test
    public void handleMemberAddedEvent() throws HouseholdNotFoundException {
        assertEquals(3, household.getHouseholdMembers().size());
        MemberAddedEvent event = new MemberAddedEvent("event1", "member1", "name", householdId, LocalDateTime.of(1900,1,1,1,1));

        consumer.handleMemberAddedEvent(event);

        assertEquals(4, household.getHouseholdMembers().size());

        Mockito.verify(householdRepository, times(1)).updateHousehold(any());
    }*/
}