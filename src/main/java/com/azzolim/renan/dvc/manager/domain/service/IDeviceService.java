package com.azzolim.renan.dvc.manager.domain.service;

import com.azzolim.renan.dvc.manager.domain.model.Device;

import java.util.List;

public interface IDeviceService {

    Device save(Device device);
    Device findById(Long id);
    List<Device> findAll();
    Device update(Long id, Device device);
    void delete(Device device);

}
