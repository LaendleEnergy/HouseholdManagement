package at.fhv.master.laendleenergy.persistence;

import at.fhv.master.laendleenergy.domain.DeviceCategory;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class DeviceRepositoryImpl implements DeviceRepository {
    @Override
    @Transactional
    public void addDevice(String name, DeviceCategory category) {

    }

    @Override
    @Transactional
    public void removeDevice(String deviceId) {

    }

    @Override
    @Transactional
    public void addDeviceCategory(String name) {

    }

    @Override
    @Transactional
    public void removeDeviceCategory(String name) {

    }
}
