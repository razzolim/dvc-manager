package com.azzolim.renan.dvc.manager.api.v1.controller.openapi;

import com.azzolim.renan.dvc.manager.api.exceptionhandler.Problem;
import com.azzolim.renan.dvc.manager.api.v1.controller.in.DeviceInput;
import com.azzolim.renan.dvc.manager.api.v1.controller.in.PurchaseServiceInput;
import com.azzolim.renan.dvc.manager.api.v1.controller.out.DeviceOutput;
import com.azzolim.renan.dvc.manager.api.v1.controller.out.ServiceOutput;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.List;

@Api(tags = "Devices")
public interface DeviceControllerOpenApi {

    @ApiOperation("Register a device")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Device registered"),
            @ApiResponse(code = 409, message = "A record already exists with same values")
    })
    DeviceOutput save(
            @ApiParam(name = "body", value = "Representation of a device", required = true) DeviceInput input
    );

    @ApiOperation("Presents all device.")
    List<DeviceOutput> findAll();

    @ApiOperation("Searches a device by its id.")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Invalid device id", response = Problem.class),
            @ApiResponse(code = 404, message = "Device not found", response = Problem.class)
    })
    DeviceOutput findById(
            @ApiParam(value = "Device id", example = "1", required = true) Long id
    );

    @ApiOperation("Deletes a device by its id")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Device removed"),
            @ApiResponse(code = 404, message = "Device not found", response = Problem.class)
    })
    void delete(Long id);

    @ApiOperation("Updates a device by its id")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Device updated"),
            @ApiResponse(code = 404, message = "Device not found", response = Problem.class)
    })
    DeviceOutput update(
            @ApiParam(value = "Device id", example = "1", required = true) Long id,
            @ApiParam(name = "body", value = "Representation of a device", required = true) DeviceInput input);


    @ApiOperation("Returns all services by a device id")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Device updated"),
            @ApiResponse(code = 404, message = "Device not found", response = Problem.class),
            @ApiResponse(code = 404, message = "Service not found", response = Problem.class)
    })
    List<ServiceOutput> getServicesByDeviceId(
            @ApiParam(value = "Device id", example = "1", required = true) Long id
    );

    @ApiOperation("Returns all services by a device id")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Service added"),
            @ApiResponse(code = 404, message = "Device not found", response = Problem.class),
            @ApiResponse(code = 404, message = "Service not found", response = Problem.class)
    })
    void addServiceToDevice(
            @ApiParam(value = "Device id", example = "1", required = true) Long id,
            @ApiParam(name = "body", value = "Representation of a service", required = true) PurchaseServiceInput input);



    @ApiOperation("Returns all services by a device id")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Service added"),
            @ApiResponse(code = 404, message = "Device not found", response = Problem.class),
            @ApiResponse(code = 404, message = "Service not found", response = Problem.class)
    })
    void removeServiceFromDevice(
            @ApiParam(value = "Device id", example = "1", required = true) Long id,
            @ApiParam(value = "Service id", example = "1", required = true) Long svcId);
}
