package at.fhv.master.laendleenergy.application;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class DeviceServiceImpl implements DeviceService {
    @Override
    @Transactional
    public void addDevice(String name, String category) {

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
