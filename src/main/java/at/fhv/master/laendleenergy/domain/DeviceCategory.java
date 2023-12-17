package at.fhv.master.laendleenergy.domain;

import java.util.HashMap;
import java.util.Map;

public enum DeviceCategory {
    FRIDGE("Kühlschrank"),
    HAIR_DRYER("Haarföhn"),
    MICROWAVE("Mikrowelle");

    private final String name;

    private static final Map<String, DeviceCategory> lookup = new HashMap<String, DeviceCategory>();

    static {
        for (DeviceCategory c : DeviceCategory.values()) {
            lookup.put(c.getName(), c);
        }
    }

    DeviceCategory(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static DeviceCategory get(String name) {
        return lookup.get(name);
    }
}
