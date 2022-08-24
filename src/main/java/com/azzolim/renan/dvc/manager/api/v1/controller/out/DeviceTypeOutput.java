package com.azzolim.renan.dvc.manager.api.v1.controller.out;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeviceTypeOutput {

    @ApiModelProperty(example = "1")
    private Long id;

    @ApiModelProperty(example = "Mac")
    private String name;

}
