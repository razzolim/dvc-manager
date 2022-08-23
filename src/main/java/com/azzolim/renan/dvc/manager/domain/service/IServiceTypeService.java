package com.azzolim.renan.dvc.manager.domain.service;

import com.azzolim.renan.dvc.manager.domain.model.ServiceType;

import java.util.List;

public interface IServiceTypeService {
    ServiceType save(ServiceType serviceType);
    ServiceType findById(Long id);
    List<ServiceType> findAll();
    void delete(ServiceType serviceType);
}
