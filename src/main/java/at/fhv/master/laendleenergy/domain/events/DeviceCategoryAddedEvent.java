package at.fhv.master.laendleenergy.domain.events;

import java.time.LocalDateTime;
import java.util.UUID;

public class DeviceCategoryAddedEvent extends Event {
    private String name;

    private String deviceId;

    public DeviceCategoryAddedEvent() {}

    public DeviceCategoryAddedEvent(String deviceId, String memberId, String name, String householdId) {
        super(UUID.randomUUID().toString(), memberId, householdId, LocalDateTime.now());
        this.deviceId = deviceId;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setName(String name) {
        this.name = name;
    }


}
