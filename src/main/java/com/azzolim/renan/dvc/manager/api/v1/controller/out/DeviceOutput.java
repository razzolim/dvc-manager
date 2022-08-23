package com.azzolim.renan.dvc.manager.api.v1.controller.out;

import com.azzolim.renan.dvc.manager.domain.model.DeviceType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeviceOutput {
    private Long id;
    private String systemName;
    private DeviceType deviceType;
}
