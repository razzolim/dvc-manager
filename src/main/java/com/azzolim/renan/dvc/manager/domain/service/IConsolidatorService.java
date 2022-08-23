package com.azzolim.renan.dvc.manager.domain.service;

import java.math.BigDecimal;

public interface IConsolidatorService {

    BigDecimal consolidateAll();
    BigDecimal consolidateByDevice(Long deviceId);

}
