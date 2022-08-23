package com.azzolim.renan.dvc.manager.api.v1.controller.in;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class ServiceTypeInput {

    @Min(5)
    @NotBlank
    private String name;

}