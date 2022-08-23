package com.azzolim.renan.dvc.manager.domain.service.impl;

import com.azzolim.renan.dvc.manager.domain.exception.EntityAlreadyExistsException;
import com.azzolim.renan.dvc.manager.domain.exception.EntityInUseException;
import com.azzolim.renan.dvc.manager.domain.exception.EntityNotFoundException;
import com.azzolim.renan.dvc.manager.domain.model.Service;
import com.azzolim.renan.dvc.manager.domain.repository.ServiceRepository;
import com.azzolim.renan.dvc.manager.domain.service.IServiceService;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;

@org.springframework.stereotype.Service
public class ServiceServiceImpl implements IServiceService {

    private final ServiceRepository repository;

    public ServiceServiceImpl(ServiceRepository repository) {
        this.repository = repository;
    }

    @Override
    public Service save(Service service) {
        try {
            return this.repository.save(service);
        } catch (DataIntegrityViolationException ex) {
            throw new EntityAlreadyExistsException("Service already registered.");
        }
    }

    @Override
    public List<Service> findAll() {
        return this.repository.findAll();
    }

    @Override
    public Service findById(Long id) {
        return this.repository.findById(id).orElseThrow(() ->
             new EntityNotFoundException(String.format("No service with ID '%s' found.", id))
        );
    }

    @Override
    public void delete(Service service) {
        try {
            this.repository.delete(service);
        } catch (DataIntegrityViolationException ex) {
            throw new EntityInUseException("This record cannot be deleted because it is in use by another entity.");
        }
    }

}
