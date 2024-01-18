package at.fhv.master.laendleenergy.application.streams.consumer;

import at.fhv.master.laendleenergy.application.streams.EventHandler;
import at.fhv.master.laendleenergy.domain.events.MemberRemovedEvent;
import at.fhv.master.laendleenergy.domain.exceptions.HouseholdNotFoundException;
import io.quarkus.redis.datasource.RedisDataSource;
import io.quarkus.redis.datasource.pubsub.PubSubCommands;
import io.quarkus.runtime.Startup;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.function.Consumer;

@ApplicationScoped
@Startup
public class MemberRemovedEventConsumer implements Consumer<MemberRemovedEvent> {

    @Inject
    EventHandler eventHandler;

    public MemberRemovedEventConsumer(RedisDataSource ds) {
        PubSubCommands<MemberRemovedEvent> pub = ds.pubsub(MemberRemovedEvent.class);
        pub.subscribe("MemberRemovedEvent", this);
    }

    @Override
    public void accept(MemberRemovedEvent memberRemovedEvent) {
        try {
            eventHandler.handleMemberRemovedEvent(memberRemovedEvent);
        } catch (HouseholdNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}