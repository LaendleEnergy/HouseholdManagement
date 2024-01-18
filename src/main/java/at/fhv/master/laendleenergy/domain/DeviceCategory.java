package at.fhv.master.laendleenergy.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class DeviceCategory {


    @Id
    @Column(name = "category_name")
    private String categoryName;


    public DeviceCategory(String name) {
        this.categoryName = name;
    }

    public DeviceCategory() {

    }

    public String getCategoryName() {
        return categoryName;
    }
}
