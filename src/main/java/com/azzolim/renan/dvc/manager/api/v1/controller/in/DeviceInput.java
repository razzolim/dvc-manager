package com.azzolim.renan.dvc.manager.api.v1.controller.in;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DeviceInput {

    @ApiModelProperty(example = "MacOSX")
    @NotBlank
    private String systemName;

    @ApiModelProperty(example = "1")
    @NotNull
    private Long deviceTypeId;

}
