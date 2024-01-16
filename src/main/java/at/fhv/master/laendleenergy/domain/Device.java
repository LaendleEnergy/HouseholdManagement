package at.fhv.master.laendleenergy.domain;

import jakarta.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name="device")
public class Device {
    @Column(name="device_name")
    @Id
    private String name;
    @ManyToOne
    @JoinColumn(name = "household_id")
    @Id
    private Household household;
    @ManyToOne
    @JoinColumn(name = "category_name")
    private DeviceCategory deviceCategory;

    public Device(DeviceCategory deviceCategory, String name, Household household) {
        this.deviceCategory = deviceCategory;
        this.name = name;
        this.household = household;
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

    public Household getHousehold() {
        return household;
    }

    public void setHousehold(Household household) {
        this.household = household;
    }

}
