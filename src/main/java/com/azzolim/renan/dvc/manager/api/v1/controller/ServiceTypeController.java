package com.azzolim.renan.dvc.manager.api.v1.controller;

import com.azzolim.renan.dvc.manager.api.v1.controller.in.ServiceTypeInput;
import com.azzolim.renan.dvc.manager.api.v1.controller.openapi.ServiceTypeControllerOpenApi;
import com.azzolim.renan.dvc.manager.api.v1.controller.out.ServiceTypeOutput;
import com.azzolim.renan.dvc.manager.domain.model.ServiceType;
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
@RequestMapping(value = "/v1/service-types", produces = MediaType.APPLICATION_JSON_VALUE)
public class ServiceTypeController implements ServiceTypeControllerOpenApi {

    private final IServiceTypeService service;
    private final ModelMapper mapper;

    public ServiceTypeController(IServiceTypeService service, ModelMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ServiceTypeOutput save(@RequestBody @Valid ServiceTypeInput input) {
        var serviceType = ServiceType.builder().name(input.getName()).build();
        return this.mapper.map(this.service.save(serviceType), ServiceTypeOutput.class);
    }

    @GetMapping
    public List<ServiceTypeOutput> findAll() {
        return this.service.findAll().stream()
                .map(st -> this.mapper.map(st, ServiceTypeOutput.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ServiceTypeOutput findById(@PathVariable Long id) {
        return this.mapper.map(this.service.findById(id), ServiceTypeOutput.class);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        var serviceType = this.service.findById(id);
        this.service.delete(serviceType);
    }
}
