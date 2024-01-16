package at.fhv.master.laendleenergy.domain.serializer;

import at.fhv.master.laendleenergy.domain.events.DeviceAddedEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.util.LinkedList;
import java.util.List;

public class DeviceAddedEventSerializer {

    public DeviceAddedEventSerializer() {}

    public static String parse(DeviceAddedEvent deviceAddedEvent) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper.writeValueAsString(deviceAddedEvent);
    }
}
