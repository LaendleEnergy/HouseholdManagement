package at.fhv.master.laendleenergy.application.streams.publisher;

import at.fhv.master.laendleenergy.domain.events.DeviceCategoryAddedEvent;
import at.fhv.master.laendleenergy.domain.serializer.DeviceCategoryAddedEventSerializer;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

@QuarkusTest
@TestTransaction
public class DeviceCategoryCreatedEventPublisherTests {
    @Inject
    DeviceCategoryCreatedEventPublisher pub;

    @Test
    public void testConnection() throws JsonProcessingException {
        DeviceCategoryAddedEvent event = new DeviceCategoryAddedEvent("d1", "memberid", "name", "h1");
        pub.publishMessage(DeviceCategoryAddedEventSerializer.parse(event));
    }
}

