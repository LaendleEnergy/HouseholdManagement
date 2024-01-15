package at.fhv.master.laendleenergy.application.streams.consumer;

import at.fhv.master.laendleenergy.application.streams.EventHandler;
import at.fhv.master.laendleenergy.domain.EnergySavingTarget;
import at.fhv.master.laendleenergy.domain.Household;
import at.fhv.master.laendleenergy.domain.Incentive;
import at.fhv.master.laendleenergy.domain.events.TaggingCreatedEvent;
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
import java.util.Map;
import static org.mockito.ArgumentMatchers.anyString;

@QuarkusTest
@TestTransaction
public class TaggingCreatedEventConsumerTests {
    @Inject
    TaggingCreatedEventConsumer consumer;
    @InjectMock
    EventHandler eventHandler;

    @ConfigProperty(name = "redis-host")  private String REDIS_HOST;
    @ConfigProperty(name = "redis-port")  private String REDIS_PORT;
    @ConfigProperty(name = "redis-tagging-created-key")  private String KEY;
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
    }

    @Test
    public void testConnection() throws HouseholdNotFoundException {
        Mockito.doNothing().when(eventHandler).handleTaggingCreatedEvent(anyString(), anyString());

        RedisClient redisClient = RedisClient.create("redis://" + REDIS_HOST + ":" + REDIS_PORT);
        StatefulRedisConnection<String, String> connection = redisClient.connect();

        try (connection) {
            RedisCommands<String, String> syncCommands = connection.sync();
            TaggingCreatedEvent event = new TaggingCreatedEvent("event1", LocalDateTime.now(), "member1", "D1", householdId);

            Map<String, String> messageBody = new HashMap<>();
            messageBody.put("eventId", event.getEventId());
            messageBody.put("taggingTime", event.getTimestamp().toString());
            messageBody.put("userId", event.getMemberId());
            messageBody.put("deviceId", event.getDeviceId());
            messageBody.put("householdId", event.getHouseholdId());

            syncCommands.xadd(KEY, messageBody);
        } finally {
            redisClient.shutdown();
        }

        consumer.consume();
    }
}
