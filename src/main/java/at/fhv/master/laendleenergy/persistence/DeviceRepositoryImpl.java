package at.fhv.master.laendleenergy.persistence;

import at.fhv.master.laendleenergy.domain.Device;
import at.fhv.master.laendleenergy.domain.DeviceCategory;
import at.fhv.master.laendleenergy.domain.exceptions.DeviceCategoryNotFound;
import at.fhv.master.laendleenergy.domain.exceptions.DeviceNotFoundException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class DeviceRepositoryImpl implements DeviceRepository {


    @Inject
    EntityManager eM;

    @Override
    public DeviceCategory getDeviceCategoryByName(String deviceCategoryName) throws DeviceCategoryNotFound {
        DeviceCategory deviceCategory = eM.find(DeviceCategory.class, deviceCategoryName);
        if(deviceCategory == null) throw new DeviceCategoryNotFound();

        return deviceCategory;
    }

    @Override
    @Transactional
    public void addDevice(Device device) {
        eM.persist(device);
    }

    @Override
    @Transactional
    public void removeDevice(String deviceName, String householdId) throws DeviceNotFoundException {
        Device toRemove = eM.createQuery("FROM Device WHERE " +
                "name = :deviceName AND household = :householdId", Device.class)
                .setParameter("deviceName", deviceName)
                .setParameter("householdId", householdId)
                .getSingleResult();
        if (toRemove == null) throw new DeviceNotFoundException();
        eM.remove(toRemove);
    }

    @Override
    @Transactional
    public void addDeviceCategory(DeviceCategory deviceCategory) {
        eM.persist(deviceCategory);
    }

    @Override
    @Transactional
    public void removeDeviceCategory(String name) throws DeviceCategoryNotFound {
        DeviceCategory toRemove = eM.find(DeviceCategory.class, name);
        if (toRemove == null) throw new DeviceCategoryNotFound();
        eM.remove(toRemove);
    }

    @Override
    public List<DeviceCategory> getAllDeviceCategories() {
        return eM.createQuery("FROM DeviceCategory", DeviceCategory.class).getResultList();
    }
}
