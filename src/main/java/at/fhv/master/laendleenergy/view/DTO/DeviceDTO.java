package at.fhv.master.laendleenergy.view.DTO;

import at.fhv.master.laendleenergy.domain.Device;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DeviceDTO {
    @JsonProperty
    String name;
    @JsonProperty
    String categoryName;

    public DeviceDTO(String name, String categoryName) {
        this.name = name;
        this.categoryName = categoryName;
    }


    public DeviceDTO(){

    }

    public String getName() {
        return name;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

}
