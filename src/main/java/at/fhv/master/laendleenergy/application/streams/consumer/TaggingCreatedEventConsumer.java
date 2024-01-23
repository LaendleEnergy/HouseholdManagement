package at.fhv.master.laendleenergy.application.streams.consumer;

import at.fhv.master.laendleenergy.application.streams.EventHandler;
import at.fhv.master.laendleenergy.domain.events.TaggingCreatedEvent;
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
public class TaggingCreatedEventConsumer implements Consumer<TaggingCreatedEvent> {

    @Inject
    EventHandler eventHandler;

    public TaggingCreatedEventConsumer(RedisDataSource ds) {
        PubSubCommands<TaggingCreatedEvent> pub = ds.pubsub(TaggingCreatedEvent.class);
        pub.subscribe("TaggingCreatedEvent", this);
    }

    @Override
    public void accept(TaggingCreatedEvent taggingCreatedEvent) {
        CompletableFuture.runAsync(() -> {
            try {
                eventHandler.handleTaggingCreatedEvent(taggingCreatedEvent);
            } catch (HouseholdNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
    }
}