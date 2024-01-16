package at.fhv.master.laendleenergy.domain.events;

import java.time.LocalDateTime;
import java.util.UUID;

public class DeviceCategoryAddedEvent extends Event {
    private String name;

    public DeviceCategoryAddedEvent() {}

    public DeviceCategoryAddedEvent(String memberId, String name, String householdId) {
        super(UUID.randomUUID().toString(), memberId, householdId, LocalDateTime.now());
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
