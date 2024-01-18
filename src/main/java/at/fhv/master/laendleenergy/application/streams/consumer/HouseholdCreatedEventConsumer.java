package at.fhv.master.laendleenergy.application.streams.consumer;

import at.fhv.master.laendleenergy.application.streams.EventHandler;
import at.fhv.master.laendleenergy.domain.events.HouseholdCreatedEvent;
import io.quarkus.redis.datasource.RedisDataSource;
import io.quarkus.redis.datasource.pubsub.PubSubCommands;
import io.quarkus.runtime.Startup;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.function.Consumer;

@ApplicationScoped
@Startup
public class HouseholdCreatedEventConsumer implements Consumer<HouseholdCreatedEvent> {

    @Inject
    EventHandler eventHandler;

    public HouseholdCreatedEventConsumer(RedisDataSource ds) {
        PubSubCommands<HouseholdCreatedEvent> pub = ds.pubsub(HouseholdCreatedEvent.class);
        pub.subscribe("HouseholdCreatedEvent", this);
    }

    @Override
    public void accept(HouseholdCreatedEvent householdCreatedEvent) {
        eventHandler.handleHouseholdCreatedEvent(householdCreatedEvent);
    }

}
