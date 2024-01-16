package at.fhv.master.laendleenergy.view.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DeviceDTO {
    @JsonProperty
    String deviceName;
    @JsonProperty
    String deviceCategoryName;
    @JsonProperty
    String householdId;

    public DeviceDTO(String deviceName, String deviceCategoryName, String householdId) {
        this.deviceName = deviceName;
        this.deviceCategoryName = deviceCategoryName;
        this.householdId = householdId;
    }


    public DeviceDTO(){

    }

    public String getDeviceName() {
        return deviceName;
    }

    public String getDeviceCategoryName() {
        return deviceCategoryName;
    }

    public String getHouseholdId() {
        return householdId;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public void setDeviceCategoryName(String deviceCategoryName) {
        this.deviceCategoryName = deviceCategoryName;
    }

    public void setHouseholdId(String householdId) {
        this.householdId = householdId;
    }
}
