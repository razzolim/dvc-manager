package com.azzolim.renan.dvc.manager.api.v1.controller.in;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DeviceTypeInput {

    @ApiModelProperty(example = "Mac")
    @NotBlank
    private String name;

}
