package at.fhv.master.laendleenergy.domain.events;

import java.time.LocalDateTime;
import java.util.UUID;

public class DeviceAddedEvent extends Event {
    private String deviceName;
    private String deviceCategoryName;

    private String deviceId;

    public DeviceAddedEvent() {}

    public DeviceAddedEvent(String deviceId, String memberId, String name, String householdId,  String deviceCategoryName) {
        super(UUID.randomUUID().toString(), memberId, householdId, LocalDateTime.now());
        this.deviceId = deviceId;
        this.deviceName = name;
        this.deviceCategoryName = deviceCategoryName;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public String getDeviceCategoryName() {
        return deviceCategoryName;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public void setDeviceCategoryName(String deviceCategoryName) {
        this.deviceCategoryName = deviceCategoryName;
    }
}
