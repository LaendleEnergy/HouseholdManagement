package at.fhv.master.laendleenergy.application.streams.publisher;

import at.fhv.master.laendleenergy.domain.events.DeviceAddedEvent;
import at.fhv.master.laendleenergy.domain.serializer.DeviceAddedEventSerializer;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

@QuarkusTest
@TestTransaction
public class DeviceAddedEventPublisherTests {
    @Inject
    DeviceAddedEventPublisher pub;

    @Test
    public void testConnection() throws JsonProcessingException {
        DeviceAddedEvent event = new DeviceAddedEvent("event1", "d1", "memberid", "name", "h1", "c1", LocalDateTime.now());
        pub.publishMessage(DeviceAddedEventSerializer.parse(event));
    }
}

