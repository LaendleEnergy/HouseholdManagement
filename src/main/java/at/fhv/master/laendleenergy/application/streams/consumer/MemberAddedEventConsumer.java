package at.fhv.master.laendleenergy.application.streams.consumer;

import at.fhv.master.laendleenergy.application.streams.EventHandler;
import at.fhv.master.laendleenergy.domain.Household;
import at.fhv.master.laendleenergy.domain.HouseholdMember;
import at.fhv.master.laendleenergy.domain.events.MemberAddedEvent;
import at.fhv.master.laendleenergy.domain.exceptions.HouseholdNotFoundException;
import at.fhv.master.laendleenergy.persistence.HouseholdRepository;
import io.quarkus.scheduler.Scheduled;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import io.lettuce.core.*;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class MemberAddedEventConsumer {

    @Inject
    EventHandler eventHandler;
    @ConfigProperty(name = "redis-host")  private String redisHost;
    @ConfigProperty(name = "redis-port")  private String redisPort;
    @ConfigProperty(name = "redis-member-added-key")  private String KEY;
    @ConfigProperty(name = "redis-accountmanagement-group")  private String GROUP_NAME;

    RedisCommands<String, String> syncCommands;

    public MemberAddedEventConsumer(){

    }

    @PostConstruct
    public void connect() {
        RedisClient redisClient = RedisClient.create("redis://" + redisHost + ":" + redisPort);
        StatefulRedisConnection<String, String> connection = redisClient.connect();
        syncCommands = connection.sync();

        initialize();
    }

    protected void initialize() {
        if (syncCommands.exists(KEY) == 0) {
            Map<String, String> messageBody = new HashMap<>();
            messageBody.put( "testcreatekey", "testcreatevalue" );
            String id = syncCommands.xadd(KEY, messageBody);
            syncCommands.xdel(KEY, id);
        }

        List<Object> groups = syncCommands.xinfoGroups(KEY);
        boolean groupAlreadyExists = false;
        for (Object obj : groups) {
            //object returned by redis is a list of parameters that need to be casted
            if (((List<String>)obj).contains(GROUP_NAME)) {
                groupAlreadyExists = true;
            }
        }
        if (!groupAlreadyExists) {
            syncCommands.xgroupCreate(XReadArgs.StreamOffset.latest(KEY), GROUP_NAME, new XGroupCreateArgs());
        }
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
