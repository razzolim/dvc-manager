package com.azzolim.renan.dvc.manager.api.v1.controller;

import com.azzolim.renan.dvc.manager.api.v1.controller.in.ServiceCostInput;
import com.azzolim.renan.dvc.manager.api.v1.controller.openapi.ServiceCostControllerOpenApi;
import com.azzolim.renan.dvc.manager.api.v1.controller.out.ServiceCostOutput;
import com.azzolim.renan.dvc.manager.domain.model.ServiceCost;
import com.azzolim.renan.dvc.manager.domain.service.IDeviceTypeService;
import com.azzolim.renan.dvc.manager.domain.service.IServiceCostService;
import com.azzolim.renan.dvc.manager.domain.service.IServiceService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/v1/service-costs", produces = MediaType.APPLICATION_JSON_VALUE)
public class ServiceCostController implements ServiceCostControllerOpenApi {

    private final IServiceCostService costServiceImpl;
    private final IServiceService serviceServiceImpl;
    private final IDeviceTypeService devTypeServiceImpl;
    private final ModelMapper mapper;

    public ServiceCostController(IServiceCostService scService,
                                 IServiceService serviceServiceImpl,
                                 IDeviceTypeService dtService,
                                 ModelMapper mapper) {
        this.serviceServiceImpl = serviceServiceImpl;
        this.devTypeServiceImpl = dtService;
        this.costServiceImpl = scService;
        this.mapper = mapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ServiceCostOutput save(@RequestBody @Valid ServiceCostInput input) {
        var service = this.serviceServiceImpl.findById(input.getServiceId());
        var devType = this.devTypeServiceImpl.findById(input.getDeviceTypeId());
        var cost = ServiceCost.builder()
                .amount(input.getAmount())
                .service(service)
                .deviceType(devType)
                .build();
        return this.mapper.map(this.costServiceImpl.save(cost), ServiceCostOutput.class);
    }

    @GetMapping
    public List<ServiceCostOutput> findAll() {
        return this.costServiceImpl.findAll().stream()
                .map(sc -> this.mapper.map(sc, ServiceCostOutput.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ServiceCostOutput findById(@PathVariable Long id) {
        return this.mapper.map(this.costServiceImpl.findById(id), ServiceCostOutput.class);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        var serviceCost = this.costServiceImpl.findById(id);
        this.costServiceImpl.delete(serviceCost);
    }
}
