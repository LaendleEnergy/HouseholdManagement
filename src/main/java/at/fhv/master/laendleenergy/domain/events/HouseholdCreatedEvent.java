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

}
