package com.azzolim.renan.dvc.manager.api.v1.controller.out;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServiceOutput {

    @ApiModelProperty(example = "1")
    private Long id;


    @ApiModelProperty(example = "Backup system")
    private String name;


    @ApiModelProperty(example = "1")
    private ServiceTypeOutput serviceType;
}
