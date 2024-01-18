package at.fhv.master.laendleenergy.view.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DeviceDTO {
    @JsonProperty
    String deviceName;
    @JsonProperty
    String deviceCategoryName;

    public DeviceDTO(String deviceName, String deviceCategoryName) {
        this.deviceName = deviceName;
        this.deviceCategoryName = deviceCategoryName;
    }


    public DeviceDTO(){

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
