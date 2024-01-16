package at.fhv.master.laendleenergy.view.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DeviceCategoryDTO {

    @JsonProperty("name")
    String name;

    public DeviceCategoryDTO(String name) {
        this.name = name;
    }

    public DeviceCategoryDTO(){

    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
