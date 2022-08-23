package com.azzolim.renan.dvc.manager.api.v1.controller.in;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Getter
@Setter
public class ServiceCostInput {

    @NotNull
    private Long serviceId;

    @NotNull
    private Long deviceTypeId;

    @NotNull
    @PositiveOrZero
    private BigDecimal amount;

}
