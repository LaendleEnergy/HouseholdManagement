package at.fhv.master.laendleenergy.application.streams.consumer;

import at.fhv.master.laendleenergy.application.streams.EventHandler;
import at.fhv.master.laendleenergy.domain.events.MemberRemovedEvent;
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
public class MemberRemovedEventConsumerTests {
    @Inject
    MemberRemovedEventConsumer consumer;
    @InjectMock
    EventHandler eventHandler;

    @Test
    public void testAccept() throws HouseholdNotFoundException {
        MemberRemovedEvent event = new MemberRemovedEvent("event1", "member1", "household1", LocalDateTime.of(2000,1,1,1,1,1));
        consumer.accept(event);

        Mockito.verify(eventHandler, times(1)).handleMemberRemovedEvent(event);
    }
}
