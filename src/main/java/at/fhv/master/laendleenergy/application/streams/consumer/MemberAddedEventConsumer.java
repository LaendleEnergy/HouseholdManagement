package at.fhv.master.laendleenergy.application.streams.consumer;

import at.fhv.master.laendleenergy.application.streams.EventHandler;
import at.fhv.master.laendleenergy.domain.events.MemberAddedEvent;
import at.fhv.master.laendleenergy.domain.exceptions.HouseholdNotFoundException;
import io.quarkus.redis.datasource.RedisDataSource;
import io.quarkus.redis.datasource.pubsub.PubSubCommands;
import io.quarkus.runtime.Startup;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.function.Consumer;

@ApplicationScoped
@Startup
public class MemberAddedEventConsumer implements Consumer<MemberAddedEvent> {

    @Inject
    EventHandler eventHandler;

    public MemberAddedEventConsumer(RedisDataSource ds) {
        PubSubCommands<MemberAddedEvent> pub = ds.pubsub(MemberAddedEvent.class);
        pub.subscribe("MemberAddedEvent", this);
    }

    @Override
    public void accept(MemberAddedEvent memberAddedEvent) {
        try {
            eventHandler.handleMemberAddedEvent(memberAddedEvent);
        } catch (HouseholdNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}