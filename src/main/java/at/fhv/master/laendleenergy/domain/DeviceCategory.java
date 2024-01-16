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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DeviceCategory that = (DeviceCategory) o;

        return categoryName.equals(that.categoryName);
    }

    @Override
    public int hashCode() {
        return categoryName.hashCode();
    }
}
