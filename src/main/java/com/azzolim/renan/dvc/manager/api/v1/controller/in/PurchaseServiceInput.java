package com.azzolim.renan.dvc.manager.api.v1.controller.in;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class PurchaseServiceInput {
    @NotNull
    private Long serviceId;
}
