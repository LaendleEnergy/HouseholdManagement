package at.fhv.master.laendleenergy.datacollector.application.streams.consumer;


import at.fhv.master.laendleenergy.datacollector.application.streams.EventHandler;
import at.fhv.master.laendleenergy.datacollector.model.events.household.DeviceCategoryAddedEvent;
import io.quarkus.redis.datasource.RedisDataSource;
import io.quarkus.redis.datasource.pubsub.PubSubCommands;
import io.quarkus.runtime.Startup;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.function.Consumer;


@ApplicationScoped
@Startup
public class DeviceCategoryAddedEventConsumer implements Consumer<DeviceCategoryAddedEvent> {
    @Inject
    EventHandler eventHandler;

    private final PubSubCommands<DeviceCategoryAddedEvent> pub;
    private final PubSubCommands.RedisSubscriber subscriber;


    public DeviceCategoryAddedEventConsumer(RedisDataSource ds) {
        pub = ds.pubsub(DeviceCategoryAddedEvent.class);
        subscriber = pub.subscribe("DeviceCategoryAddedEvent", this);
    }

    @Override
    public void accept(DeviceCategoryAddedEvent deviceCategoryAddedEvent) {
        eventHandler.handleDeviceCategoryAddedEvent(deviceCategoryAddedEvent);
    }

}
