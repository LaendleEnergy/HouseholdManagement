package at.fhv.master.laendleenergy.application;

public interface DeviceService {
    void addDevice(String name, String category);
    void removeDevice(String deviceId);
    void addDeviceCategory(String name);
    void removeDeviceCategory(String name);
}
