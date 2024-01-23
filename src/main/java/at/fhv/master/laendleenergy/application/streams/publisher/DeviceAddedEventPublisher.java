package at.fhv.master.laendleenergy.application.streams.publisher;

import io.quarkus.runtime.Startup;
import io.quarkus.redis.client.RedisClient;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
@Startup
public class DeviceAddedEventPublisher {
    @Inject
    RedisClient redisClient;
    @ConfigProperty(name = "redis-device-added-key")
    private String KEY;

    public DeviceAddedEventPublisher(){}

    public void publishMessage(String message) {
        redisClient.publish(KEY, message);
    }
}