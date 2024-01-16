package at.fhv.master.laendleenergy.application;

import at.fhv.master.laendleenergy.application.streams.publisher.DeviceCategoryCreatedEventPublisher;
import at.fhv.master.laendleenergy.application.streams.publisher.DeviceCreatedEventPublisher;
import at.fhv.master.laendleenergy.domain.Device;
import at.fhv.master.laendleenergy.domain.DeviceCategory;
import at.fhv.master.laendleenergy.domain.Household;
import at.fhv.master.laendleenergy.domain.events.DeviceAddedEvent;
import at.fhv.master.laendleenergy.domain.events.DeviceCategoryAddedEvent;
import at.fhv.master.laendleenergy.domain.exceptions.DeviceCategoryNotFound;
import at.fhv.master.laendleenergy.domain.exceptions.DeviceNotFoundException;
import at.fhv.master.laendleenergy.domain.exceptions.HouseholdNotFoundException;
import at.fhv.master.laendleenergy.domain.serializer.DeviceAddedEventSerializer;
import at.fhv.master.laendleenergy.domain.serializer.DeviceCategoryAddedEventSerializer;
import at.fhv.master.laendleenergy.persistence.DeviceRepository;
import at.fhv.master.laendleenergy.persistence.HouseholdRepository;
import at.fhv.master.laendleenergy.view.DTO.DeviceCategoryDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class DeviceServiceImpl implements DeviceService {


    @Inject
    HouseholdRepository householdRepository;

    @Inject
    DeviceRepository deviceRepository;


    @Inject
    DeviceCreatedEventPublisher deviceCreatedEventPublisher;

    @Inject
    DeviceCategoryCreatedEventPublisher deviceCategoryCreatedEventPublisher;


    @Inject
    JsonWebToken jwt;


    @Override
    @Transactional
    public void addDevice(String name, String category) throws HouseholdNotFoundException, DeviceCategoryNotFound, JsonProcessingException {
        String memberId = jwt.getClaim("memberId");
        String householdId = jwt.getClaim("householdId");
        Household household = householdRepository.getHouseholdById(householdId);
        DeviceCategory categoryLoaded = deviceRepository.getDeviceCategoryByName(category);
        Device device = new Device(categoryLoaded, name, household);
        deviceRepository.addDevice(device);
        deviceCreatedEventPublisher.publishMessage(
                DeviceAddedEventSerializer.parse(
                        new DeviceAddedEvent(memberId, device.getName(), householdId, categoryLoaded.getCategoryName())
                )
        );
    }

    @Override
    @Transactional
    public void removeDevice(String deviceId) throws DeviceNotFoundException {
        String memberId = jwt.getClaim("memberId");
        String householdId = jwt.getClaim("householdId");
        deviceRepository.removeDevice(deviceId, householdId);
    }

    @Override
    @Transactional
    public void addDeviceCategory(String name) throws JsonProcessingException {
        String memberId = jwt.getClaim("memberId");
        String householdId = jwt.getClaim("householdId");
        DeviceCategory deviceCategory = new DeviceCategory(name);
        deviceRepository.addDeviceCategory(deviceCategory);
        deviceCategoryCreatedEventPublisher.publishMessage(
                DeviceCategoryAddedEventSerializer.parse(new DeviceCategoryAddedEvent(
                        memberId, deviceCategory.getCategoryName(), householdId
                ))
        );
    }

    @Override
    @Transactional
    public void removeDeviceCategory(String name) throws DeviceCategoryNotFound {
        String memberId = jwt.getClaim("memberId");
        String householdId = jwt.getClaim("householdId");
        deviceRepository.removeDeviceCategory(name);
    }

    @Override
    public List<DeviceCategoryDTO> getAllAvailableDeviceCategories() {
        return deviceRepository.getAllDeviceCategories()
                .stream().map(
                        deviceCategory -> new DeviceCategoryDTO(deviceCategory.getCategoryName())
                ).collect(Collectors.toList());
    }
}
