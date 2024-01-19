package at.fhv.master.laendleenergy.persistence;

import at.fhv.master.laendleenergy.domain.*;
import at.fhv.master.laendleenergy.domain.exceptions.DeviceCategoryNotFound;
import at.fhv.master.laendleenergy.domain.exceptions.DeviceNotFoundException;
import io.quarkus.test.InjectMock;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import org.hibernate.query.Query;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;

@QuarkusTest
@TestTransaction
public class DeviceRepositoryTests {
    @InjectMock
    EntityManager entityManager;
    @Inject
    DeviceRepository deviceRepository;

    static Device device;
    static final String deviceName = "fancyname";
    static DeviceCategory deviceCategory;
    static final String deviceCategoryName = "Kühlschrank";
    static Household household;
    static final String householdId = "householdId1";

    @BeforeEach
    void setUp() {
        household = new Household(householdId,
                new Incentive("Pizza", LocalDate.of(2050, 10, 31)),
                new EnergySavingTarget(10, "Vormonat"),
                new LinkedList<>(),
                new LinkedList<>());

        deviceCategory = new DeviceCategory(deviceCategoryName);
        device = new Device(deviceCategory, deviceName, household);
    }

    @Test
    public void addDeviceTest() {
        deviceRepository.addDevice(device);

        Mockito.verify(entityManager, times(1)).persist(device);
    }

    @Test
    public void removeDeviceTest() throws DeviceNotFoundException {
        Query<Device> queryMock = Mockito.mock(Query.class);

        Mockito.when(entityManager.createQuery(Mockito.anyString(), Mockito.eq(Device.class))).thenReturn(queryMock);
        Mockito.when(queryMock.setParameter(Mockito.anyString(), Mockito.any())).thenReturn(queryMock);
        Mockito.when(queryMock.getSingleResult()).thenReturn(device);

        deviceRepository.removeDevice(deviceName, household);

        Mockito.verify(entityManager, times(1)).remove(device);
    }

    @Test
    public void removeDeviceTestException() {
        assertThrows(DeviceNotFoundException.class, () -> deviceRepository.removeDevice(deviceName, household));

        Mockito.verify(entityManager, times(0)).remove(device);
    }

    @Test
    public void addDeviceCategoryTest() {
        deviceRepository.addDeviceCategory(deviceCategory);

        Mockito.verify(entityManager, times(1)).persist(deviceCategory);
    }

    @Test
    public void removeDeviceCategoryTest() throws DeviceCategoryNotFound {
        Mockito.when(entityManager.find(DeviceCategory.class, deviceCategoryName)).thenReturn(deviceCategory);

        deviceRepository.removeDeviceCategory(deviceCategoryName);

        Mockito.verify(entityManager, times(1)).remove(deviceCategory);
    }

    @Test
    public void removeDeviceCategoryTestException() {
        assertThrows(DeviceCategoryNotFound.class, () -> deviceRepository.removeDeviceCategory(deviceCategoryName));

        Mockito.verify(entityManager, times(0)).remove(deviceCategory);
    }

    @Test
    public void getDeviceCategoryByNameTest() throws DeviceCategoryNotFound {
        Mockito.when(entityManager.find(DeviceCategory.class, deviceCategoryName)).thenReturn(deviceCategory);

        DeviceCategory actualDeviceCategory = deviceRepository.getDeviceCategoryByName(deviceCategoryName);
        assertEquals(actualDeviceCategory, deviceCategory);
    }

    @Test
    public void getDeviceCategoryByNameTest_DeviceDoesNotExist() {
        assertThrows(DeviceCategoryNotFound.class, () -> deviceRepository.getDeviceCategoryByName(deviceCategoryName));
    }

    @Test
    public void getAllDeviceCategoriesTest() {
        Query<DeviceCategory> queryMock = Mockito.mock(Query.class);

        Mockito.when(entityManager.createQuery(Mockito.anyString(), Mockito.eq(DeviceCategory.class))).thenReturn(queryMock);
        Mockito.when(queryMock.getResultList()).thenReturn(List.of(new DeviceCategory(), new DeviceCategory()));

        List<DeviceCategory> actualCategories = deviceRepository.getAllDeviceCategories();
        assertEquals(2, actualCategories.size());
    }
}
