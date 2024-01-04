package at.fhv.master.laendleenergy.unit;

import at.fhv.master.laendleenergy.domain.DeviceCategory;
import at.fhv.master.laendleenergy.domain.serializer.DeviceCategorySerializer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Link;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
public class SerializerTests {
    /*@Test
    public void deviceCategorySerializerTest() throws JsonProcessingException {
        List<String> expectedParsedValues = List.of(
                DeviceCategory.FRIDGE.toString(),
                DeviceCategory.MICROWAVE.toString(),
                DeviceCategory.HAIR_DRYER.toString()
        );

        List<String> actualParsedValues = DeviceCategorySerializer.parse();

        assertEquals(actualParsedValues, expectedParsedValues);
    }*/
}
