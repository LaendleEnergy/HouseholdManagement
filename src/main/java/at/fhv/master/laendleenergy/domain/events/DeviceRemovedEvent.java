package at.fhv.master.laendleenergy.domain.events;

import java.time.LocalDateTime;
import java.util.UUID;

public class DeviceRemovedEvent extends Event {
    private String deviceName;
    private String deviceCategoryName;

    public DeviceRemovedEvent() {}

    public DeviceRemovedEvent(String memberId, String name, String householdId, LocalDateTime timestamp, String deviceCategoryName) {
        super(UUID.randomUUID().toString(), memberId, householdId, timestamp);
        this.deviceName = name;
        this.deviceCategoryName = deviceCategoryName;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public String getDeviceCategoryName() {
        return deviceCategoryName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public void setDeviceCategoryName(String deviceCategoryName) {
        this.deviceCategoryName = deviceCategoryName;
    }
}
