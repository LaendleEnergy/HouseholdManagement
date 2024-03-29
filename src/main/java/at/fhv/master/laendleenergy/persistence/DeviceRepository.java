package at.fhv.master.laendleenergy.persistence;

import at.fhv.master.laendleenergy.domain.Device;
import at.fhv.master.laendleenergy.domain.DeviceCategory;
import at.fhv.master.laendleenergy.domain.Household;
import at.fhv.master.laendleenergy.domain.exceptions.DeviceCategoryNotFound;
import at.fhv.master.laendleenergy.domain.exceptions.DeviceNotFoundException;
import at.fhv.master.laendleenergy.view.DTO.DeviceCategoryDTO;

import java.util.List;

public interface DeviceRepository {


    DeviceCategory getDeviceCategoryByName(String name) throws DeviceCategoryNotFound;

    void addDevice(Device device);
    void removeDevice(String deviceName, Household household) throws DeviceNotFoundException;
    void updateDevice(Device device) throws DeviceNotFoundException;
    void addDeviceCategory(DeviceCategory deviceCategory);
    void removeDeviceCategory(String name) throws DeviceCategoryNotFound;

    List<DeviceCategory> getAllDeviceCategories();
}
