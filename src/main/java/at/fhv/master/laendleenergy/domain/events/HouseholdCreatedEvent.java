package at.fhv.master.laendleenergy.domain.events;

import io.lettuce.core.StreamMessage;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class HouseholdCreatedEvent extends Event {
    private String name;

    public HouseholdCreatedEvent() {}

    public HouseholdCreatedEvent(String eventId, String memberId, String name, String householdId, LocalDateTime timestamp) {
        super(eventId, memberId, householdId, timestamp);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static HouseholdCreatedEvent fromStreamMessage(StreamMessage<String, String> message) {
        Map<String, String> body = message.getBody();

        String eventId = body.get("eventId");
        String memberId = body.get("memberId");
        String name = body.get("name");
        String householdId = body.get("householdId");

        String timestampString = body.get("timestamp");
        LocalDateTime timestamp = LocalDateTime.parse(timestampString, DateTimeFormatter.ISO_DATE_TIME);

        return new HouseholdCreatedEvent(eventId, memberId, name, householdId, timestamp);
    }

}
