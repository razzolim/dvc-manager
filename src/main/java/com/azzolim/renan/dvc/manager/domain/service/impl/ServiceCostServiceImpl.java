package com.azzolim.renan.dvc.manager.domain.service.impl;

import com.azzolim.renan.dvc.manager.domain.exception.EntityAlreadyExistsException;
import com.azzolim.renan.dvc.manager.domain.exception.EntityNotFoundException;
import com.azzolim.renan.dvc.manager.domain.model.ServiceCost;
import com.azzolim.renan.dvc.manager.domain.repository.ServiceCostRepository;
import com.azzolim.renan.dvc.manager.domain.service.IServiceCostService;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;

@org.springframework.stereotype.Service
public class ServiceCostServiceImpl implements IServiceCostService {

    private final ServiceCostRepository repository;

    public ServiceCostServiceImpl(ServiceCostRepository repository) {
        this.repository = repository;
    }

    @Override
    public ServiceCost save(ServiceCost serviceCost) {
        try {
            return this.repository.save(serviceCost);
        } catch (DataIntegrityViolationException ex) {
            throw new EntityAlreadyExistsException("This service cost already exists for this type of device and service.");
        }
    }

    @Override
    public List<ServiceCost> findAll() {
        return this.repository.findAll();
    }

    @Override
    public void delete(ServiceCost cost) {
        this.repository.delete(cost);
    }

    @Override
    public ServiceCost findById(Long id) {
        return this.repository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(String.format("No service cost with ID '%s' found.", id))
        );
    }


}
