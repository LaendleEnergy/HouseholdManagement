package at.fhv.master.laendleenergy.application.streams.consumer;

import at.fhv.master.laendleenergy.domain.Household;
import at.fhv.master.laendleenergy.domain.events.HouseholdCreatedEvent;
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
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;

@QuarkusTest
@TestTransaction
public class HouseholdCreatedEventConsumerTests {
    @Inject
    HouseholdCreatedEventConsumer consumer;
    @InjectMock
    HouseholdRepository householdRepository;

    @ConfigProperty(name = "redis-host")  private String redisHost;
    @ConfigProperty(name = "redis-port")  private String redisPort;
    @ConfigProperty(name = "redis-household-created-key")  private String KEY;

    static Household household;
    static final String householdId = "h1";
    static final String memberId = "m1";

    @BeforeEach
    void setUp() throws HouseholdNotFoundException {
        Mockito.when(householdRepository.getHouseholdById(anyString())).thenReturn(household);
    }
    @Test
    public void testConnection() throws HouseholdNotFoundException {
        RedisClient redisClient = RedisClient.create("redis://" + redisHost + ":" + redisPort);
        StatefulRedisConnection<String, String> connection = redisClient.connect();

        try (connection) {
            RedisCommands<String, String> syncCommands = connection.sync();
            HouseholdCreatedEvent event = new HouseholdCreatedEvent("event1", memberId, "name", householdId, LocalDateTime.now());

            Map<String, String> messageBody = new HashMap<>();
            messageBody.put("eventId", event.getEventId());
            messageBody.put("memberId", event.getMemberId());
            messageBody.put("name", event.getName());
            messageBody.put("householdId", event.getHouseholdId());
            messageBody.put("timestamp", event.getTimestamp().toString());

            syncCommands.xadd(KEY, messageBody);

            System.out.println(messageBody);
        } finally {
            redisClient.shutdown();
        }

        consumer.consume();
    }
}
