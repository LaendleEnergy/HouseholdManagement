package at.fhv.master.laendleenergy.domain.events;

import io.lettuce.core.StreamMessage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class MemberRemovedEvent extends Event {
    private String eventId;
    private String memberId;
    private String householdId;
    private LocalDateTime timestamp;

    public MemberRemovedEvent() {}

    public MemberRemovedEvent(String eventId, String memberId, String householdId, LocalDateTime timestamp) {
        super(eventId, memberId, householdId, timestamp);
    }

    public static MemberRemovedEvent fromStreamMessage(StreamMessage<String, String> message) {
        Map<String, String> body = message.getBody();

        String eventId = body.get("eventId");
        String memberId = body.get("memberId");
        String householdId = body.get("householdId");

        String timestampString = body.get("timestamp");
        LocalDateTime timestamp = LocalDateTime.parse(timestampString, DateTimeFormatter.ISO_DATE_TIME);

        return new MemberRemovedEvent(eventId, memberId, householdId, timestamp);
    }
}
