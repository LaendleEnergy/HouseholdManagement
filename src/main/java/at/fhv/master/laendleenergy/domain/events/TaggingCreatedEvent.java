package at.fhv.master.laendleenergy.domain.events;

import io.lettuce.core.StreamMessage;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class TaggingCreatedEvent extends Event {
    private String deviceId;

    public TaggingCreatedEvent() {}

    public TaggingCreatedEvent(String eventId, LocalDateTime timestamp, String memberId, String deviceId, String householdId) {
        super(eventId, memberId, householdId, timestamp);
        this.deviceId = deviceId;
    }


    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
}