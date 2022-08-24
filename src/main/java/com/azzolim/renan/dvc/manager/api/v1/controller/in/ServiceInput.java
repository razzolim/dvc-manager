package com.azzolim.renan.dvc.manager.api.v1.controller.in;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ServiceInput {

    @NotBlank
    private String name;

    @NotNull
    private Long serviceTypeId;

}
