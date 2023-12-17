package at.fhv.master.laendleenergy.domain;

public class Device {
    private DeviceCategory deviceCategory;
    private String name;

    public Device(DeviceCategory deviceCategory, String name) {
        this.deviceCategory = deviceCategory;
        this.name = name;
    }

    public Device() {
    }

    public DeviceCategory getDeviceCategory() {
        return deviceCategory;
    }

    public void setDeviceCategory(DeviceCategory deviceCategory) {
        this.deviceCategory = deviceCategory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
