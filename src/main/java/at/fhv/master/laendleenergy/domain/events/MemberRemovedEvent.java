package at.fhv.master.laendleenergy.domain.events;

import io.lettuce.core.StreamMessage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class MemberRemovedEvent extends Event {

    public MemberRemovedEvent() {}

    public MemberRemovedEvent(String eventId, String memberId, String householdId, LocalDateTime timestamp) {
        super(eventId, memberId, householdId, timestamp);
    }
}
