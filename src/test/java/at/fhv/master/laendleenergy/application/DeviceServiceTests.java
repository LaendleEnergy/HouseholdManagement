package at.fhv.master.laendleenergy.application;

import at.fhv.master.laendleenergy.domain.*;
import at.fhv.master.laendleenergy.domain.exceptions.DeviceCategoryNotFound;
import at.fhv.master.laendleenergy.domain.exceptions.DeviceNotFoundException;
import at.fhv.master.laendleenergy.domain.exceptions.HouseholdNotFoundException;
import at.fhv.master.laendleenergy.persistence.DeviceRepository;
import at.fhv.master.laendleenergy.persistence.HouseholdRepository;
import at.fhv.master.laendleenergy.view.DTO.DeviceCategoryDTO;
import at.fhv.master.laendleenergy.view.DTO.DeviceDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.quarkus.test.InjectMock;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;

@QuarkusTest
@TestTransaction
public class DeviceServiceTests {
    @InjectMock
    DeviceRepository deviceRepository;
    @InjectMock
    HouseholdRepository householdRepository;
    @Inject
    DeviceService deviceService;
    static Device device;
    static final String deviceId = "deviceId";
    static final String deviceName = "fancyname";
    static DeviceCategory deviceCategory;
    static final String deviceCategoryName = "Kuehlschrank";

    static Household household;
    static final String householdId = "householdId1";

    @BeforeEach
    void setUp() throws HouseholdNotFoundException, DeviceCategoryNotFound {
        household = new Household(householdId,
                new Incentive("Pizza", LocalDate.of(2050, 10, 31)),
                new EnergySavingTarget(10, "Vormonat"),
                new LinkedList<>(),
                new LinkedList<>());
        deviceCategory = new DeviceCategory(deviceCategoryName);
        device = new Device(deviceCategory, deviceName, household);
        household.setDevices(List.of(device));

        Mockito.when(householdRepository.getHouseholdById(householdId)).thenReturn(household);
        Mockito.when(deviceRepository.getDeviceCategoryByName(deviceCategoryName)).thenReturn(deviceCategory);
        List<DeviceCategory> deviceCategories = List.of(new DeviceCategory(), new DeviceCategory());
        Mockito.when(deviceRepository.getAllDeviceCategories()).thenReturn(deviceCategories);
        Mockito.when(householdRepository.getDevicesOfHousehold(householdId)).thenReturn(List.of(new Device(deviceCategory, deviceName, household), new Device(deviceCategory, deviceName, household)));
    }


    @Test
    public void addDeviceTest() throws HouseholdNotFoundException, DeviceCategoryNotFound, JsonProcessingException {
        deviceService.addDevice(device.getName(), deviceCategoryName, deviceId, householdId, "m1");

        Mockito.verify(deviceRepository, times(1)).addDevice(any());
    }

    @Test
    public void removeDeviceTest() throws DeviceNotFoundException, HouseholdNotFoundException {
        deviceService.removeDevice(deviceName, householdId);

        Mockito.verify(deviceRepository, times(1)).removeDevice(deviceName, household);
    }

    @Test
    public void getAllAvailableDeviceCategoriesTest() {
        List<DeviceCategoryDTO> actual = deviceService.getAllAvailableDeviceCategories();
        List<DeviceCategoryDTO> expected = List.of(new DeviceCategoryDTO(deviceCategoryName), new DeviceCategoryDTO());

        assertEquals(expected.size(), actual.size());
        Mockito.verify(deviceRepository, times(1)).getAllDeviceCategories();
    }

    @Test
    public void getDevicesOfHouseholdTest() throws HouseholdNotFoundException {
        List<DeviceDTO> actual = deviceService.getDevicesOfHousehold(householdId);
        List<DeviceDTO> expected = List.of(new DeviceDTO(deviceName, deviceCategoryName), new DeviceDTO(deviceName, deviceCategoryName));

        assertEquals(expected.size(), actual.size());
        Mockito.verify(householdRepository, times(1)).getDevicesOfHousehold(anyString());
    }

    @Test
    public void updateDeviceTest() throws HouseholdNotFoundException, DeviceNotFoundException, DeviceCategoryNotFound {
        DeviceDTO deviceDTO = new DeviceDTO(device.getName(), "name");
        deviceDTO.setCategoryName(device.getDeviceCategory().getCategoryName());

        deviceService.updateDevice(deviceDTO, householdId);

        Mockito.verify(householdRepository, times(1)).getHouseholdById(anyString());
        Mockito.verify(deviceRepository, times(1)).updateDevice(any());
        Mockito.verify(deviceRepository, times(1)).getDeviceCategoryByName(anyString());
    }
}
