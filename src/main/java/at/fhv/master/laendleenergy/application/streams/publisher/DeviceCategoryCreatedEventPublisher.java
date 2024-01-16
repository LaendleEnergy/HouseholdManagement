package at.fhv.master.laendleenergy.application.streams.publisher;

import io.quarkus.redis.client.RedisClient;
import io.quarkus.runtime.Startup;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
@Startup
public class DeviceCategoryCreatedEventPublisher {
    @Inject
    RedisClient redisClient;
    @ConfigProperty(name = "redis-device-category-added-key")
    private String KEY;

    public DeviceCategoryCreatedEventPublisher(){}

    public void publishMessage(String message) {
        redisClient.publish(KEY, message);
    }
}