package com.azzolim.renan.dvc.manager.api.v1.controller.out;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ConsolidatorOutput {

    public ConsolidatorOutput(BigDecimal total) {
        this.total = total;
    }

    @ApiModelProperty(example = "10.0")
    private BigDecimal total;
}
