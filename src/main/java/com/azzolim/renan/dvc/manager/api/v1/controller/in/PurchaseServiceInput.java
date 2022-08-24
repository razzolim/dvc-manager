package com.azzolim.renan.dvc.manager.api.v1.controller.in;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class PurchaseServiceInput {

    @ApiModelProperty(example = "1")
    @NotNull
    private Long serviceId;
}
