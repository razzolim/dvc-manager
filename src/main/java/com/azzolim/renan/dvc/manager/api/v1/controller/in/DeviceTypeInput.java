package com.azzolim.renan.dvc.manager.api.v1.controller.in;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DeviceTypeInput {

    @Min(5)
    @NotBlank
    private String name;

}
