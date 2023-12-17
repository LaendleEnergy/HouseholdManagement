package at.fhv.master.laendleenergy.domain.serializer;

import at.fhv.master.laendleenergy.domain.DeviceCategory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.LinkedList;
import java.util.List;

public class DeviceCategorySerializer {

    public DeviceCategorySerializer() {}

    public static List<String> parse() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<String> parsed = new LinkedList<>();

        for (DeviceCategory e : DeviceCategory.values()) {
            parsed.add(objectMapper.writeValueAsString(e));
        }

        return parsed;
    }
}
