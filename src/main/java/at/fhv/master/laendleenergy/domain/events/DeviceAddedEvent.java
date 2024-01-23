package at.fhv.master.laendleenergy.domain.events;

import java.time.LocalDateTime;
import java.util.UUID;

public class DeviceAddedEvent extends Event {
    private String name;
    private String categoryName;

    private String deviceId;

    public DeviceAddedEvent() {}

    public DeviceAddedEvent(String eventId, String deviceId, String memberId, String name, String householdId,  String categoryName, LocalDateTime timestamp) {
        super(eventId, memberId, householdId, timestamp);
        this.deviceId = deviceId;
        this.name = name;
        this.categoryName = categoryName;
    }

    public String getName() {
        return name;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
