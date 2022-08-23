package com.azzolim.renan.dvc.manager.api.v1.controller.out;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServiceOutput {
    private Long id;
    private String name;
    private ServiceTypeOutput serviceType;
}
