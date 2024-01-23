package at.fhv.master.laendleenergy.domain.events;

import java.time.LocalDateTime;

public class MemberUpdatedEvent extends Event {
    private String name;

    public MemberUpdatedEvent() {
    }

    public MemberUpdatedEvent(String eventId, String memberId, String name, String householdId, LocalDateTime timestamp) {
        super(eventId, memberId, householdId, timestamp);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
