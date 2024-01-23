package at.fhv.master.laendleenergy.application.streams.consumer;

import at.fhv.master.laendleenergy.application.streams.EventHandler;
import at.fhv.master.laendleenergy.domain.events.MemberAddedEvent;
import at.fhv.master.laendleenergy.domain.events.MemberUpdatedEvent;
import at.fhv.master.laendleenergy.domain.exceptions.HouseholdNotFoundException;
import io.quarkus.redis.datasource.RedisDataSource;
import io.quarkus.redis.datasource.pubsub.PubSubCommands;
import io.quarkus.runtime.Startup;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

@ApplicationScoped
@Startup
public class MemberUpdatedEventConsumer implements Consumer<MemberUpdatedEvent> {

    @Inject
    EventHandler eventHandler;

    public MemberUpdatedEventConsumer(RedisDataSource ds) {
        PubSubCommands<MemberUpdatedEvent> pub = ds.pubsub(MemberUpdatedEvent.class);
        pub.subscribe("MemberUpdatedEvent", this);
    }

    @Override
    public void accept(MemberUpdatedEvent memberupdatedEvent) {
        CompletableFuture.runAsync(() -> {
            try {
                eventHandler.handleMemberUpdatedEvent(memberupdatedEvent);
            } catch (HouseholdNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
    }
}