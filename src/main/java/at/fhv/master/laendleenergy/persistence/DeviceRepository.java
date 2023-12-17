package at.fhv.master.laendleenergy.persistence;

import at.fhv.master.laendleenergy.domain.DeviceCategory;

public interface DeviceRepository {

    void addDevice(String name, DeviceCategory category);
    void removeDevice(String deviceId);
    void addDeviceCategory(String name);
    void removeDeviceCategory(String name);
}
