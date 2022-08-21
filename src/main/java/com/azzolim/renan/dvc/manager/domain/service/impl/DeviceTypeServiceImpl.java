package com.azzolim.renan.dvc.manager.domain.service.impl;

import com.azzolim.renan.dvc.manager.domain.exception.EntityAlreadyExistsException;
import com.azzolim.renan.dvc.manager.domain.exception.EntityInUseException;
import com.azzolim.renan.dvc.manager.domain.exception.EntityNotFoundException;
import com.azzolim.renan.dvc.manager.domain.model.DeviceType;
import com.azzolim.renan.dvc.manager.domain.repository.DeviceTypeRepository;
import com.azzolim.renan.dvc.manager.domain.service.IDeviceTypeService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class DeviceTypeServiceImpl implements IDeviceTypeService {

    private final DeviceTypeRepository repository;

    public DeviceTypeServiceImpl(DeviceTypeRepository repository) {
        this.repository = repository;
    }

    @Override
    public DeviceType save(DeviceType deviceType) {
        try {
            return this.repository.save(deviceType);
        } catch (DataIntegrityViolationException ex) {
            throw new EntityAlreadyExistsException(String.format("Device type with name %s already exists.", deviceType.getName()));
        }
    }

    @Override
    public List<DeviceType> findAll() {
        return this.repository.findAll();
    }

    @Override
    public DeviceType findById(Long id) {
        return this.repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("No device type with ID '%s' found.", id))
        );
    }

    @Override
    public void delete(DeviceType deviceType) {
        try {
            this.repository.delete(deviceType);
        } catch (DataIntegrityViolationException ex) {
            throw new EntityInUseException("This record cannot be deleted because it is in use by another entity.");
        }
    }
}
