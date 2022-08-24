package com.azzolim.renan.dvc.manager.api.v1.controller.out;

import com.azzolim.renan.dvc.manager.domain.model.DeviceType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ServiceCostOutput {

    @ApiModelProperty(example = "1")
    private Long id;

    @ApiModelProperty(example = "1.0")
    private BigDecimal amount;

    private ServiceOutput service;
    private DeviceType deviceType;
}
