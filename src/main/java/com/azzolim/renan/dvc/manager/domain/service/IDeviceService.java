package com.azzolim.renan.dvc.manager.domain.service;

import com.azzolim.renan.dvc.manager.domain.model.Device;
import com.azzolim.renan.dvc.manager.domain.model.Service;

import java.util.List;

public interface IDeviceService {

    Device save(Device device);
    Device findById(Long id);
    List<Device> findAll();
    Device update(Long id, Device device);
    void delete(Device device);
    void addService(Device device, Service service);
    void removeService(Device device, Long serviceId);
}
