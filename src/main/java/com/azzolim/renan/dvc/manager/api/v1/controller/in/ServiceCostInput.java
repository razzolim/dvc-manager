package com.azzolim.renan.dvc.manager.api.v1.controller.in;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Getter
@Setter
public class ServiceCostInput {


    @ApiModelProperty(example = "1")
    @NotNull
    private Long serviceId;


    @ApiModelProperty(example = "1")
    @NotNull
    private Long deviceTypeId;


    @ApiModelProperty(example = "1.0")
    @NotNull
    @PositiveOrZero
    private BigDecimal amount;

}
