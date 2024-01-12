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

    public static TaggingCreatedEvent fromStreamMessage(StreamMessage<String, String> message) {
        Map<String, String> body = message.getBody();

        String eventId = body.get("eventId");
        String userId = body.get("userId");
        String deviceId = body.get("deviceId");
        String householdId = body.get("householdId");

        String timestampString = body.get("taggingTime");
        LocalDateTime timestamp = LocalDateTime.parse(timestampString, DateTimeFormatter.ISO_DATE_TIME);

        return new TaggingCreatedEvent(eventId, timestamp, userId, deviceId, householdId);
    }
}