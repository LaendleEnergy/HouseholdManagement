package at.fhv.master.laendleenergy.application.streams.consumer;

import at.fhv.master.laendleenergy.application.streams.EventHandler;
import at.fhv.master.laendleenergy.domain.events.MemberAddedEvent;
import at.fhv.master.laendleenergy.domain.exceptions.HouseholdNotFoundException;
import io.lettuce.core.Consumer;
import io.lettuce.core.RedisClient;
import io.lettuce.core.StreamMessage;
import io.lettuce.core.XReadArgs;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import io.quarkus.scheduler.Scheduled;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.util.List;

@ApplicationScoped
public class MemberAddedEventConsumer extends EventConsumer {

    @Inject
    EventHandler eventHandler;
    @ConfigProperty(name = "redis-member-added-key")  private String KEY;
    @ConfigProperty(name = "redis-accountmanagement-group")  private String GROUP_NAME;

    RedisCommands<String, String> syncCommands;

    public MemberAddedEventConsumer(){

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
    public void consume() throws HouseholdNotFoundException {
        List<StreamMessage<String, String>> messages = syncCommands.xreadgroup(
                Consumer.from(GROUP_NAME, "consumer_1"),
                XReadArgs.StreamOffset.lastConsumed(KEY)
        );

        if (!messages.isEmpty()) {
            for (StreamMessage<String, String> m : messages) {
                MemberAddedEvent event = MemberAddedEvent.fromStreamMessage(m);
                eventHandler.handleMemberAddedEvent(event);
                // Confirm that the message has been processed using XACK
                syncCommands.xack(KEY, GROUP_NAME, m.getId());
            }
        }
    }
}