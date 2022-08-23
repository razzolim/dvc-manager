package com.azzolim.renan.dvc.manager.api.v1.controller;

import com.azzolim.renan.dvc.manager.api.v1.controller.in.DeviceInput;
import com.azzolim.renan.dvc.manager.api.v1.controller.out.DeviceOutput;
import com.azzolim.renan.dvc.manager.domain.model.Device;
import com.azzolim.renan.dvc.manager.domain.model.DeviceType;
import com.azzolim.renan.dvc.manager.domain.service.IDeviceService;
import com.azzolim.renan.dvc.manager.domain.service.IDeviceTypeService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/v1/devices", produces = MediaType.APPLICATION_JSON_VALUE)
public class DeviceController {

    private final IDeviceService service;
    private final IDeviceTypeService dtService;
    private final ModelMapper mapper;

    public DeviceController(IDeviceService service, IDeviceTypeService dtService, ModelMapper mapper) {
        this.service = service;
        this.dtService = dtService;
        this.mapper = mapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DeviceOutput save(@RequestBody @Valid DeviceInput input) {
        var deviceType = this.dtService.findById(input.getDeviceTypeId());
        var device = Device.builder().systemName(input.getSystemName()).deviceType(deviceType).build();
        return this.mapper.map(this.service.save(device), DeviceOutput.class);
    }

    @GetMapping
    public List<DeviceOutput> findAll() {
        var list = this.service.findAll();
        return list.stream()
                .map(d -> this.mapper.map(d, DeviceOutput.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public DeviceOutput findById(@PathVariable Long id) {
        return this.mapper.map(this.service.findById(id), DeviceOutput.class);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        var device = this.service.findById(id);
        this.service.delete(device);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public DeviceOutput update(@PathVariable Long id, @RequestBody @Valid DeviceInput input) {
        var deviceType = DeviceType.builder().id(input.getDeviceTypeId()).build();
        var device = Device.builder().systemName(input.getSystemName()).deviceType(deviceType).build();
        return this.mapper.map(this.service.update(id, device), DeviceOutput.class);
    }


}
