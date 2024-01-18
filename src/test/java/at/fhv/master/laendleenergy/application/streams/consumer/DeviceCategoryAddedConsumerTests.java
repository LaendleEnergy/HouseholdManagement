package at.fhv.master.laendleenergy.application.streams.consumer;

import at.fhv.master.laendleenergy.application.streams.EventHandler;
import at.fhv.master.laendleenergy.domain.events.DeviceCategoryAddedEvent;
import at.fhv.master.laendleenergy.domain.exceptions.HouseholdNotFoundException;
import io.quarkus.test.InjectMock;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.mockito.Mockito.times;

@QuarkusTest
@TestTransaction
public class DeviceCategoryAddedConsumerTests {
    @Inject
    DeviceCategoryAddedEventConsumer consumer;
    @InjectMock
    EventHandler eventHandler;

    @Test
    public void testAccept() throws HouseholdNotFoundException {
        DeviceCategoryAddedEvent event = new DeviceCategoryAddedEvent("d1", "m1", "name1", "h1");
        consumer.accept(event);

        Mockito.verify(eventHandler, times(1)).handleDeviceCategoryAddedEvent(event);
    }
}
