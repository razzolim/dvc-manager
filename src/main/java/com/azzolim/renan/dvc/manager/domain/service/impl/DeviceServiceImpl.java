package com.azzolim.renan.dvc.manager.domain.service.impl;

import com.azzolim.renan.dvc.manager.domain.exception.EntityAlreadyExistsException;
import com.azzolim.renan.dvc.manager.domain.exception.EntityInUseException;
import com.azzolim.renan.dvc.manager.domain.exception.EntityNotFoundException;
import com.azzolim.renan.dvc.manager.domain.model.Device;
import com.azzolim.renan.dvc.manager.domain.repository.DeviceRepository;
import com.azzolim.renan.dvc.manager.domain.service.IDeviceService;
import com.azzolim.renan.dvc.manager.domain.service.IDeviceTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Service
public class DeviceServiceImpl implements IDeviceService {

    private final DeviceRepository repository;
    private final IDeviceTypeService dtService;

    public DeviceServiceImpl(DeviceRepository repository, IDeviceTypeService dtService) {
        this.repository = repository;
        this.dtService = dtService;
    }

    @Override
    public Device save(Device device) {
        try {
            return this.repository.save(device);
        } catch (DataIntegrityViolationException ex) {
            throw new EntityAlreadyExistsException("Device already registered.");
        }
    }

    @Override
    public Device findById(Long id) {
        return this.repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("No device with ID '%s' found.", id))
        );
    }

    @Override
    public List<Device> findAll() {
        return this.repository.findAll();
    }

    @Override
    @Transactional
    public Device update(Long id, Device newDevice) {
        var oldDevice = this.findById(id);

        if (oldDevice.getDeviceType().getId() != newDevice.getDeviceType().getId()) {
            oldDevice.setDeviceType(this.dtService.findById(newDevice.getDeviceType().getId()));
        }

        oldDevice.setSystemName(newDevice.getSystemName());
        return this.repository.save(oldDevice);
    }

    @Override
    public void delete(Device device) {
        try {
            this.repository.delete(device);
        } catch (DataIntegrityViolationException ex) {
            throw new EntityInUseException("This record cannot be deleted because it is in use by another entity.");
        }
    }
}
