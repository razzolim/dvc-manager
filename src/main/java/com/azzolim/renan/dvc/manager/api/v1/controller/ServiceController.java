package com.azzolim.renan.dvc.manager.api.v1.controller;

import com.azzolim.renan.dvc.manager.api.v1.controller.in.ServiceInput;
import com.azzolim.renan.dvc.manager.api.v1.controller.openapi.ServiceControllerOpenApi;
import com.azzolim.renan.dvc.manager.api.v1.controller.out.ServiceOutput;
import com.azzolim.renan.dvc.manager.domain.model.Service;
import com.azzolim.renan.dvc.manager.domain.service.IServiceService;
import com.azzolim.renan.dvc.manager.domain.service.IServiceTypeService;
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
@RequestMapping(value = "/v1/services", produces = MediaType.APPLICATION_JSON_VALUE)
public class ServiceController implements ServiceControllerOpenApi {

    private final IServiceService service;

    private final IServiceTypeService stService;

    private final ModelMapper mapper;

    public ServiceController(IServiceService service, IServiceTypeService stService, ModelMapper mapper) {
        this.stService = stService;
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ServiceOutput save(@RequestBody @Valid ServiceInput input) {
        var serviceType = this.stService.findById(input.getServiceTypeId());
        var service = Service.builder().name(input.getName()).serviceType(serviceType).build();
        return this.mapper.map(this.service.save(service), ServiceOutput.class);
    };

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        var service = this.service.findById(id);
        this.service.delete(service);
    }

    @GetMapping("/{id}")
    public ServiceOutput findById(@PathVariable Long id) {
        return this.mapper.map(this.service.findById(id), ServiceOutput.class);
    }

    @GetMapping
    public List<ServiceOutput> findAll() {
        return this.service.findAll().stream()
                .map(s -> this.mapper.map(s, ServiceOutput.class))
                .collect(Collectors.toList());
    }

}
