package com.azzolim.renan.dvc.manager.api.v1.controller.out;

import com.azzolim.renan.dvc.manager.domain.model.DeviceType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ServiceCostOutput {
    private Long id;
    private BigDecimal amount;
    private ServiceOutput service;
    private DeviceType deviceType;
}
