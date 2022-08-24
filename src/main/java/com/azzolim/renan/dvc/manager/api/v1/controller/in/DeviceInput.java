package com.azzolim.renan.dvc.manager.api.v1.controller.in;

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

    @NotBlank
    private String systemName;

    @NotNull
    private Long deviceTypeId;

}
