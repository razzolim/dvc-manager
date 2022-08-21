package com.azzolim.renan.dvc.manager.domain.service;

import com.azzolim.renan.dvc.manager.domain.model.DeviceType;

import java.util.List;

public interface IDeviceTypeService {
    DeviceType save(DeviceType device);
    DeviceType findById(Long id);
    List<DeviceType> findAll();
    void delete(DeviceType device);
}
