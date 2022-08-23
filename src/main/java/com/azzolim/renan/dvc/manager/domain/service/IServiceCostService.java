package com.azzolim.renan.dvc.manager.domain.service;

import com.azzolim.renan.dvc.manager.domain.model.ServiceCost;

import java.util.List;

public interface IServiceCostService {
    ServiceCost save(ServiceCost serviceCost);
    List<ServiceCost> findAll();
    void delete(ServiceCost cost);
    ServiceCost findById(Long id);
}
