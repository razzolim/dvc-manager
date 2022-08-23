package com.azzolim.renan.dvc.manager.domain.service.impl;

import com.azzolim.renan.dvc.manager.domain.repository.DeviceRepository;
import com.azzolim.renan.dvc.manager.domain.service.IConsolidatorService;
import com.azzolim.renan.dvc.manager.domain.service.IDeviceService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ConsolidatorServiceImpl implements IConsolidatorService {

    private final DeviceRepository repository;
    private final IDeviceService deviceService;

    public ConsolidatorServiceImpl(DeviceRepository repository, IDeviceService deviceService) {
        this.repository = repository;
        this.deviceService = deviceService;
    }

    @Override
    public BigDecimal consolidateAll() {
        return this.repository.sumAllServices();
    }

    @Override
    public BigDecimal consolidateByDevice(Long deviceId) {
        var total = this.repository.sumAllServicesByDeviceId(deviceId);
        if (total == null) {
            this.deviceService.findById(deviceId);
        }
        return BigDecimal.ZERO;
    }
}
