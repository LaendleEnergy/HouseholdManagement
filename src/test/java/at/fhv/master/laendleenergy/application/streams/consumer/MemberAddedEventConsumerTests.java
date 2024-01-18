package at.fhv.master.laendleenergy.application.streams.consumer;

import at.fhv.master.laendleenergy.application.streams.EventHandler;
import at.fhv.master.laendleenergy.domain.events.MemberAddedEvent;
import at.fhv.master.laendleenergy.domain.exceptions.HouseholdNotFoundException;
import io.quarkus.test.InjectMock;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.time.LocalDateTime;
import static org.mockito.Mockito.times;

@QuarkusTest
@TestTransaction
public class MemberAddedEventConsumerTests {
    @Inject
    MemberAddedEventConsumer consumer;
    @InjectMock
    EventHandler eventHandler;

    @Test
    public void testAccept() throws HouseholdNotFoundException {
        MemberAddedEvent event = new MemberAddedEvent("event1", "member1", "name", "household1", LocalDateTime.of(2000,1,1,1,1,1));
        consumer.accept(event);

        Mockito.verify(eventHandler, times(1)).handleMemberAddedEvent(event);
    }
}
