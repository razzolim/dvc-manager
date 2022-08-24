package com.azzolim.renan.dvc.manager.api.v1.controller.out;

import com.azzolim.renan.dvc.manager.domain.model.DeviceType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeviceOutput {

    @ApiModelProperty(example = "1")
    private Long id;


    @ApiModelProperty(example = "MacOSX")
    private String systemName;

    @ApiModelProperty(example = "1")
    private DeviceType deviceType;
}
