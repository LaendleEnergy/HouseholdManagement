package at.fhv.master.laendleenergy.application.streams.consumer;

import at.fhv.master.laendleenergy.application.streams.EventHandler;
import at.fhv.master.laendleenergy.domain.events.HouseholdCreatedEvent;
import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import io.quarkus.test.InjectMock;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;

@QuarkusTest
@TestTransaction
public class HouseholdCreatedEventConsumerTests {
    @Inject
    HouseholdCreatedEventConsumer consumer;
    @InjectMock
    EventHandler eventHandler;
    @ConfigProperty(name = "redis-host")  private String REDIS_HOST;
    @ConfigProperty(name = "redis-port")  private String REDIS_PORT;
    @ConfigProperty(name = "redis-household-created-key")  private String KEY;

    @Test
    public void testConnection() {
        Mockito.doNothing().when(eventHandler).handleHouseholdCreatedEvent(any());

        RedisClient redisClient = RedisClient.create("redis://" + REDIS_HOST + ":" + REDIS_PORT);
        StatefulRedisConnection<String, String> connection = redisClient.connect();

        try (connection) {
            RedisCommands<String, String> syncCommands = connection.sync();
            HouseholdCreatedEvent event = new HouseholdCreatedEvent("event1", "member1", "name", "household1", LocalDateTime.now());

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
}
