package at.fhv.master.laendleenergy.domain;

import jakarta.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name="device")
public class Device {
    @Id
    @Column(name = "device_id")
    private String id;
    @Column(name = "device_category")
    @Enumerated(EnumType.STRING)
    private DeviceCategory deviceCategory;
    @Column(name="device_name")
    private String name;
    @ManyToOne
    @JoinColumn(name = "household_id")
    private Household household;

    public Device(DeviceCategory deviceCategory, String name, Household household) {
        this.id = UUID.randomUUID().toString();
        this.deviceCategory = deviceCategory;
        this.name = name;
        this.household = household;
    }

    public Device() {
        this.id = UUID.randomUUID().toString();
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

    public Household getHousehold() {
        return household;
    }

    public void setHousehold(Household household) {
        this.household = household;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Device device = (Device) o;
        return Objects.equals(id, device.id) && deviceCategory == device.deviceCategory && Objects.equals(name, device.name) && Objects.equals(household, device.household);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, deviceCategory, name, household);
    }

    @Override
    public String toString() {
        return "Device{" +
                "id='" + id + '\'' +
                ", deviceCategory=" + deviceCategory +
                ", name='" + name + '\'' +
                ", household=" + household +
                '}';
    }
}
