package at.fhv.master.laendleenergy.unit;

import at.fhv.master.laendleenergy.domain.DeviceCategory;
import at.fhv.master.laendleenergy.domain.events.DeviceAddedEvent;
import at.fhv.master.laendleenergy.domain.serializer.DeviceAddedEventSerializer;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
public class SerializerTests {

    @Test
    public void testDeviceAddedSerializer() throws JsonProcessingException {
        DeviceCategory category = new DeviceCategory("category-name");
        DeviceAddedEvent event = new DeviceAddedEvent("event1", "d1", "m1", "name", "h1", category.getCategoryName(), LocalDateTime.of(2000,1,1,1,1));
        String expected = "{\"eventId\":\"event1\",\"memberId\":\"m1\",\"householdId\":\"h1\",\"timestamp\":[2000,1,1,1,1],\"name\":\"name\",\"categoryName\":\"category-name\",\"deviceId\":\"d1\"}";
        String actual = DeviceAddedEventSerializer.parse(event);

        assertEquals(expected, actual);
    }
}
