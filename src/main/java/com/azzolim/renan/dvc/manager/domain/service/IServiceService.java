package com.azzolim.renan.dvc.manager.domain.service;

import com.azzolim.renan.dvc.manager.domain.model.Service;

import java.util.List;

public interface IServiceService {

    Service save(Service service);
    void delete(Service service);
    List<Service> findAll();
    Service findById(Long id);
}
