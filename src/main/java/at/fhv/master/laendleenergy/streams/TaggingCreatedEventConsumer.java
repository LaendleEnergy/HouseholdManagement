package at.fhv.master.laendleenergy.streams;

import at.fhv.master.laendleenergy.domain.Household;
import at.fhv.master.laendleenergy.domain.HouseholdMember;
import at.fhv.master.laendleenergy.domain.events.TaggingCreatedEvent;
import at.fhv.master.laendleenergy.domain.exceptions.HouseholdNotFoundException;
import at.fhv.master.laendleenergy.persistence.HouseholdRepository;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import io.lettuce.core.*;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class TaggingCreatedEventConsumer {

    @Inject
    HouseholdRepository householdRepository;
    @ConfigProperty(name = "redis-host")  private String redisHost;
    @ConfigProperty(name = "redis-port")  private String redisPort;
    @ConfigProperty(name = "redis-datacollector-key")  private String KEY;
    @ConfigProperty(name = "redis-datacollector-group")  private String GROUP_NAME;

    RedisCommands<String, String> syncCommands;

    public TaggingCreatedEventConsumer(){

    }

    @PostConstruct
    public void connect() {
        RedisClient redisClient = RedisClient.create("redis://" + redisHost + ":" + redisPort);
        StatefulRedisConnection<String, String> connection = redisClient.connect();
        syncCommands = connection.sync();

        initialize();
    }

    private void initialize() {
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

    //@Scheduled(every="10s")
    public void consume() throws HouseholdNotFoundException {
        List<StreamMessage<String, String>> messages = syncCommands.xreadgroup(
                Consumer.from(GROUP_NAME, "consumer_1"),
                XReadArgs.StreamOffset.lastConsumed(KEY)
        );

        if (!messages.isEmpty()) {
            for (StreamMessage<String, String> m : messages) {
                TaggingCreatedEvent event = TaggingCreatedEvent.fromStreamMessage(m);
                increaseNumberOfTagsForMember(event.getHouseholdId(), event.getUserId());
                // Confirm that the message has been processed using XACK
                syncCommands.xack(KEY, GROUP_NAME, m.getId());
            }
        }
    }

    public void increaseNumberOfTagsForMember(String householdId, String memberId) throws HouseholdNotFoundException {
        Household household = householdRepository.getHouseholdById(householdId);

        for (HouseholdMember member : household.getHouseholdMembers()) {
            if (member.getId().equals(memberId)) {
                member.increaseNumberOfCreatedTags();
                return;
            }
        }
    }
}
