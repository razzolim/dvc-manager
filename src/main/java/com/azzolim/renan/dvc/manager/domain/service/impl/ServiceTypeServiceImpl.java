package com.azzolim.renan.dvc.manager.domain.service.impl;

import com.azzolim.renan.dvc.manager.domain.exception.EntityAlreadyExistsException;
import com.azzolim.renan.dvc.manager.domain.exception.EntityInUseException;
import com.azzolim.renan.dvc.manager.domain.exception.EntityNotFoundException;
import com.azzolim.renan.dvc.manager.domain.model.ServiceType;
import com.azzolim.renan.dvc.manager.domain.repository.ServiceTypeRepository;
import com.azzolim.renan.dvc.manager.domain.service.IServiceTypeService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceTypeServiceImpl implements IServiceTypeService {

    private final ServiceTypeRepository repository;

    public ServiceTypeServiceImpl(ServiceTypeRepository repository) {
        this.repository = repository;
    }

    @Override
    public ServiceType save(ServiceType serviceType) {
        try {
            return this.repository.save(serviceType);
        } catch (DataIntegrityViolationException ex) {
            throw new EntityAlreadyExistsException("Service type already registered.");
        }
    }

    @Override
    public ServiceType findById(Long id) {
        return this.repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("No service type with ID '%s' found.", id))
        );
    }

    @Override
    public List<ServiceType> findAll() {
        return this.repository.findAll();
    }

    @Override
    public void delete(ServiceType serviceType) {
        try {
            this.repository.delete(serviceType);
        } catch (DataIntegrityViolationException ex) {
            throw new EntityInUseException("This record cannot be deleted because it is in use by another entity.");
        }
    }
}
