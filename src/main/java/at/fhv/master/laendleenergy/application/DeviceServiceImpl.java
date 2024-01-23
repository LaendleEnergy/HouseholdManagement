package at.fhv.master.laendleenergy.application;

import at.fhv.master.laendleenergy.application.streams.publisher.DeviceAddedEventPublisher;
import at.fhv.master.laendleenergy.domain.Device;
import at.fhv.master.laendleenergy.domain.DeviceCategory;
import at.fhv.master.laendleenergy.domain.Household;
import at.fhv.master.laendleenergy.domain.events.DeviceAddedEvent;
import at.fhv.master.laendleenergy.domain.exceptions.DeviceCategoryNotFound;
import at.fhv.master.laendleenergy.domain.exceptions.DeviceNotFoundException;
import at.fhv.master.laendleenergy.domain.exceptions.HouseholdNotFoundException;
import at.fhv.master.laendleenergy.domain.serializer.DeviceAddedEventSerializer;
import at.fhv.master.laendleenergy.persistence.DeviceRepository;
import at.fhv.master.laendleenergy.persistence.HouseholdRepository;
import at.fhv.master.laendleenergy.view.DTO.DeviceCategoryDTO;
import at.fhv.master.laendleenergy.view.DTO.DeviceDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@ApplicationScoped
public class DeviceServiceImpl implements DeviceService {

    @Inject
    HouseholdRepository householdRepository;
    @Inject
    DeviceRepository deviceRepository;
    @Inject
    DeviceAddedEventPublisher deviceAddedEventPublisher;

    @Override
    @Transactional
    public void addDevice(String name, String category, String deviceId, String householdId, String memberId) throws HouseholdNotFoundException, DeviceCategoryNotFound, JsonProcessingException {
        Household household = householdRepository.getHouseholdById(householdId);
        DeviceCategory categoryLoaded = deviceRepository.getDeviceCategoryByName(category);

        Device device = new Device(categoryLoaded, name, household);
        deviceRepository.addDevice(device);

        deviceAddedEventPublisher.publishMessage(
                DeviceAddedEventSerializer.parse(
                        new DeviceAddedEvent(UUID.randomUUID().toString(), deviceId, memberId, device.getName(), householdId, categoryLoaded.getCategoryName(), LocalDateTime.now())
                )
        );
    }

    @Override
    @Transactional
    public void removeDevice(String deviceName, String householdId) throws DeviceNotFoundException, HouseholdNotFoundException {
        Household household = householdRepository.getHouseholdById(householdId);
        deviceRepository.removeDevice(deviceName, household);
    }

    @Override
    @Transactional
    public void updateDevice(DeviceDTO deviceDTO, String householdId) throws HouseholdNotFoundException, DeviceCategoryNotFound, DeviceNotFoundException {
        Household household = householdRepository.getHouseholdById(householdId);
        DeviceCategory deviceCategory = deviceRepository.getDeviceCategoryByName(deviceDTO.getCategoryName());
        Optional<Device> device = household.getDevices().stream().filter(d -> d.getName().equals(deviceDTO.getName())).findFirst();

        if (device.isEmpty()) {
            throw new DeviceNotFoundException();
        }

        Device newDevice = device.get();
        newDevice.setDeviceCategory(deviceCategory);
        newDevice.setName(deviceDTO.getName());

        deviceRepository.updateDevice(newDevice);
    }

    @Override
    public List<DeviceCategoryDTO> getAllAvailableDeviceCategories() {
        return deviceRepository.getAllDeviceCategories()
                .stream().map(
                        deviceCategory -> new DeviceCategoryDTO(deviceCategory.getCategoryName())
                ).collect(Collectors.toList());
    }

    @Override
    public List<DeviceDTO> getDevicesOfHousehold(String householdId) throws HouseholdNotFoundException {
        List<Device> devices = householdRepository.getDevicesOfHousehold(householdId);
        return devices.stream().map(device -> new DeviceDTO(device.getName(), device.getDeviceCategory().getCategoryName())).collect(Collectors.toList());
    }
}
