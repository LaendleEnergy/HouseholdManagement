package at.fhv.master.laendleenergy.application.streams.consumer;

import at.fhv.master.laendleenergy.domain.exceptions.HouseholdNotFoundException;
import io.lettuce.core.XGroupCreateArgs;
import io.lettuce.core.XReadArgs;
import io.lettuce.core.api.sync.RedisCommands;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class EventConsumer {
    @ConfigProperty(name = "redis-host")  protected String REDIS_HOST;
    @ConfigProperty(name = "redis-port")  protected String REDIS_PORT;

    protected void initialize(RedisCommands<String, String> syncCommands, String key, String groupName) {
        if (syncCommands.exists(key) == 0) {
            Map<String, String> messageBody = new HashMap<>();
            messageBody.put( "testcreatekey", "testcreatevalue" );
            String id = syncCommands.xadd(key, messageBody);
            syncCommands.xdel(key, id);
        }

        List<Object> groups = syncCommands.xinfoGroups(key);
        boolean groupAlreadyExists = false;
        for (Object obj : groups) {
            //object returned by redis is a list of parameters that need to be casted
            if (((List<String>)obj).contains(groupName)) {
                groupAlreadyExists = true;
            }
        }
        if (!groupAlreadyExists) {
            syncCommands.xgroupCreate(XReadArgs.StreamOffset.latest(key), groupName, new XGroupCreateArgs());
        }
    }

    public abstract void consume() throws HouseholdNotFoundException;
}
