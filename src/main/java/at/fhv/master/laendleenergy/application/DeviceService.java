package at.fhv.master.laendleenergy.application;

import at.fhv.master.laendleenergy.domain.exceptions.DeviceCategoryNotFound;
import at.fhv.master.laendleenergy.domain.exceptions.DeviceNotFoundException;
import at.fhv.master.laendleenergy.domain.exceptions.HouseholdNotFoundException;
import at.fhv.master.laendleenergy.view.DTO.DeviceCategoryDTO;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface DeviceService {
    void addDevice(String name, String category) throws HouseholdNotFoundException, DeviceCategoryNotFound, JsonProcessingException;
    void removeDevice(String deviceId) throws DeviceNotFoundException;
    void addDeviceCategory(String name) throws JsonProcessingException;
    void removeDeviceCategory(String name) throws DeviceCategoryNotFound;

    List<DeviceCategoryDTO> getAllAvailableDeviceCategories();
}
