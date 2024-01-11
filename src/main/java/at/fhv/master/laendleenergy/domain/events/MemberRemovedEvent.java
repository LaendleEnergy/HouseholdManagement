package at.fhv.master.laendleenergy.domain.events;

import io.lettuce.core.StreamMessage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class MemberRemovedEvent {
    private String eventId;
    private String memberId;
    private String householdId;
    private LocalDateTime timestamp;

    public MemberRemovedEvent() {}

    public MemberRemovedEvent(String eventId, String memberId, String householdId, LocalDateTime timestamp) {
        this.eventId = eventId;
        this.memberId = memberId;
        this.householdId = householdId;
        this.timestamp = timestamp;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getHouseholdId() {
        return householdId;
    }

    public void setHouseholdId(String householdId) {
        this.householdId = householdId;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
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
