package at.fhv.master.laendleenergy.application.streams.consumer;

import at.fhv.master.laendleenergy.application.streams.EventHandler;
import at.fhv.master.laendleenergy.domain.events.HouseholdCreatedEvent;
import io.lettuce.core.*;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import io.quarkus.scheduler.Scheduled;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class HouseholdCreatedEventConsumer extends EventConsumer {

    @Inject
    EventHandler eventHandler;
    @ConfigProperty(name = "redis-household-created-key")  private String KEY;
    @ConfigProperty(name = "redis-accountmanagement-group")  private String GROUP_NAME;

    RedisCommands<String, String> syncCommands;

    public HouseholdCreatedEventConsumer(){

    }

    @PostConstruct
    public void connect() {
        RedisClient redisClient = RedisClient.create("redis://" + REDIS_HOST + ":" + REDIS_PORT);
        StatefulRedisConnection<String, String> connection = redisClient.connect();
        syncCommands = connection.sync();

        initialize(syncCommands, KEY, GROUP_NAME);
    }

    @Scheduled(every="5s")
    @Transactional
    public void consume() {
        List<StreamMessage<String, String>> messages = syncCommands.xreadgroup(
                Consumer.from(GROUP_NAME, "consumer_1"),
                XReadArgs.StreamOffset.lastConsumed(KEY)
        );

        if (!messages.isEmpty()) {
            for (StreamMessage<String, String> m : messages) {
                HouseholdCreatedEvent event = fromStreamMessage(m);
                eventHandler.handleHouseholdCreatedEvent(event);
                // Confirm that the message has been processed using XACK
                syncCommands.xack(KEY, GROUP_NAME, m.getId());
            }
        }
    }

    public HouseholdCreatedEvent fromStreamMessage(StreamMessage<String, String> message) {
        Map<String, String> body = message.getBody();

        String eventId = body.get("eventId");
        String memberId = body.get("memberId");
        String name = body.get("name");
        String householdId = body.get("householdId");

        String timestampString = body.get("timestamp");
        LocalDateTime timestamp = LocalDateTime.parse(timestampString, DateTimeFormatter.ISO_DATE_TIME);

        return new HouseholdCreatedEvent(eventId, memberId, name, householdId, timestamp);
    }


}
