package com.azzolim.renan.dvc.manager.api.v1.controller;

import com.azzolim.renan.dvc.manager.api.v1.controller.in.DeviceTypeInput;
import com.azzolim.renan.dvc.manager.api.v1.controller.out.DeviceTypeOutput;
import com.azzolim.renan.dvc.manager.domain.model.DeviceType;
import com.azzolim.renan.dvc.manager.domain.service.IDeviceTypeService;
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
@RequestMapping(value = "/v1/device-types", produces = MediaType.APPLICATION_JSON_VALUE)
public class DeviceTypeController {

    private final IDeviceTypeService service;
    private final ModelMapper mapper;

    public DeviceTypeController(IDeviceTypeService service, ModelMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DeviceTypeOutput save(@Valid @RequestBody DeviceTypeInput input) {
        var deviceType = this.service.save(this.mapper.map(input, DeviceType.class));
        return this.mapper.map(deviceType, DeviceTypeOutput.class);
    }

    @GetMapping
    public List<DeviceTypeOutput> findAll() {
        return this.service.findAll().stream()
                .map(dt -> this.mapper.map(dt, DeviceTypeOutput.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public DeviceTypeOutput findById(@PathVariable Long id) {
        return this.mapper.map(this.service.findById(id), DeviceTypeOutput.class);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        var deviceType = this.service.findById(id);
        this.service.delete(deviceType);
    }

}
