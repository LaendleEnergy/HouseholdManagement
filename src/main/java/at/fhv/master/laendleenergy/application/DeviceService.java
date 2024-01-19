package at.fhv.master.laendleenergy.application;

import at.fhv.master.laendleenergy.domain.exceptions.DeviceCategoryNotFound;
import at.fhv.master.laendleenergy.domain.exceptions.DeviceNotFoundException;
import at.fhv.master.laendleenergy.domain.exceptions.HouseholdNotFoundException;
import at.fhv.master.laendleenergy.view.DTO.DeviceCategoryDTO;
import at.fhv.master.laendleenergy.view.DTO.DeviceDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.List;

public interface DeviceService {
    void addDevice(String name, String category, String deviceId, String householdId, String memberId) throws HouseholdNotFoundException, DeviceCategoryNotFound, JsonProcessingException;
    void removeDevice(String deviceName, String householdId) throws DeviceNotFoundException, HouseholdNotFoundException;
    List<DeviceCategoryDTO> getAllAvailableDeviceCategories();
    List<DeviceDTO> getDevicesOfHousehold(String householdId) throws HouseholdNotFoundException;
}
