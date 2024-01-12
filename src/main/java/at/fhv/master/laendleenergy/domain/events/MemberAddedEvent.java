package at.fhv.master.laendleenergy.domain.events;

import io.lettuce.core.StreamMessage;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class MemberAddedEvent extends Event {
    private String name;

    public MemberAddedEvent() {
    }

    public MemberAddedEvent(String eventId, String memberId, String name, String householdId, LocalDateTime timestamp) {
        super(eventId, memberId, householdId, timestamp);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static MemberAddedEvent fromStreamMessage(StreamMessage<String, String> message) {
        Map<String, String> body = message.getBody();

        String eventId = body.get("eventId");
        String memberId = body.get("memberId");
        String householdId = body.get("householdId");
        String name = body.get("name");

        String timestampString = body.get("timestamp");
        LocalDateTime timestamp = LocalDateTime.parse(timestampString, DateTimeFormatter.ISO_DATE_TIME);

        return new MemberAddedEvent(eventId, memberId, name, householdId, timestamp);
    }
}
