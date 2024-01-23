package at.fhv.master.laendleenergy.application.streams.consumer;

import at.fhv.master.laendleenergy.application.streams.EventHandler;
import at.fhv.master.laendleenergy.domain.events.DeviceCategoryAddedEvent;
import io.quarkus.test.InjectMock;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.timeout;

@QuarkusTest
@TestTransaction
public class DeviceCategoryAddedConsumerTests {
    @Inject
    DeviceCategoryAddedEventConsumer consumer;
    @InjectMock
    EventHandler eventHandler;

    @Test
    public void testAccept() {
        DeviceCategoryAddedEvent event = new DeviceCategoryAddedEvent("event1", "d1", "m1", "name1", "h1");
        consumer.accept(event);

        Mockito.verify(eventHandler, timeout(100).times(1)).handleDeviceCategoryAddedEvent(event);
    }
}
