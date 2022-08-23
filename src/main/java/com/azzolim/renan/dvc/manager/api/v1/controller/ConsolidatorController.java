package com.azzolim.renan.dvc.manager.api.v1.controller;

import com.azzolim.renan.dvc.manager.api.v1.controller.out.ConsolidatorOutput;
import com.azzolim.renan.dvc.manager.domain.service.IConsolidatorService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v1/consolidator", produces = MediaType.APPLICATION_JSON_VALUE)
public class ConsolidatorController {

    private final IConsolidatorService service;

    public ConsolidatorController(IConsolidatorService service) {
        this.service = service;
    }

    @GetMapping("/costs")
    public ConsolidatorOutput costsConsolidatorAll() {
        return new ConsolidatorOutput(this.service.consolidateAll());
    }

    @GetMapping("/costs/{deviceId}")
    public ConsolidatorOutput costsConsolidatorByDeviceId(@PathVariable Long deviceId) {
        return new ConsolidatorOutput(this.service.consolidateByDevice(deviceId));
    }
}
