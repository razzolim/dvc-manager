package com.azzolim.renan.dvc.manager.api.v1.controller;

import com.azzolim.renan.dvc.manager.api.v1.controller.in.DeviceInput;
import com.azzolim.renan.dvc.manager.api.v1.controller.in.PurchaseServiceInput;
import com.azzolim.renan.dvc.manager.api.v1.controller.out.DeviceOutput;
import com.azzolim.renan.dvc.manager.api.v1.controller.out.ServiceOutput;
import com.azzolim.renan.dvc.manager.api.v1.controller.out.ServiceTypeOutput;
import com.azzolim.renan.dvc.manager.domain.model.Device;
import com.azzolim.renan.dvc.manager.domain.model.DeviceType;
import com.azzolim.renan.dvc.manager.domain.service.IDeviceService;
import com.azzolim.renan.dvc.manager.domain.service.IDeviceTypeService;
import com.azzolim.renan.dvc.manager.domain.service.IServiceService;
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

    private final IDeviceService deviceService;
    private final IDeviceTypeService dtService;
    private final IServiceService serviceServiceImpl;
    private final ModelMapper mapper;

    public DeviceController(IDeviceService service,
                            IDeviceTypeService dtService,
                            IServiceService serviceServiceImpl,
                            ModelMapper mapper) {
        this.serviceServiceImpl = serviceServiceImpl;
        this.dtService = dtService;
        this.deviceService = service;
        this.mapper = mapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DeviceOutput save(@Valid @RequestBody DeviceInput input) {
        var deviceType = this.dtService.findById(input.getDeviceTypeId());
        var device = Device.builder().systemName(input.getSystemName()).deviceType(deviceType).build();
        return this.mapper.map(this.deviceService.save(device), DeviceOutput.class);
    }

    @GetMapping
    public List<DeviceOutput> findAll() {
        var list = this.deviceService.findAll();
        return list.stream()
                .map(d -> this.mapper.map(d, DeviceOutput.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public DeviceOutput findById(@PathVariable Long id) {
        return this.mapper.map(this.deviceService.findById(id), DeviceOutput.class);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        var device = this.deviceService.findById(id);
        this.deviceService.delete(device);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public DeviceOutput update(@PathVariable Long id, @RequestBody @Valid DeviceInput input) {
        var deviceType = DeviceType.builder().id(input.getDeviceTypeId()).build();
        var device = Device.builder().systemName(input.getSystemName()).deviceType(deviceType).build();
        return this.mapper.map(this.deviceService.update(id, device), DeviceOutput.class);
    }

    @GetMapping("/{id}/services")
    public List<ServiceOutput> getServicesByDeviceId(@PathVariable Long id) {
        return this.deviceService.findById(id).getServices().stream()
                .map(svc -> {
                    var output = this.mapper.map(svc, ServiceOutput.class);
                    output.setServiceType(this.mapper.map(svc.getServiceType(), ServiceTypeOutput.class));
                    return output;
                })
                .collect(Collectors.toList());
    }

    @PostMapping("/{id}/services")
    @ResponseStatus(HttpStatus.CREATED)
    public void addServiceToDevice(@PathVariable Long id, @RequestBody @Valid PurchaseServiceInput input) {
        var device = this.deviceService.findById(id);
        var service = this.serviceServiceImpl.findById(input.getServiceId());
        this.deviceService.addService(device, service);
    }

    @DeleteMapping("/{id}/services/{svcId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeServiceFromDevice(@PathVariable Long id, @PathVariable Long svcId) {
        var device = this.deviceService.findById(id);
        this.deviceService.removeService(device, svcId);
    }
}
