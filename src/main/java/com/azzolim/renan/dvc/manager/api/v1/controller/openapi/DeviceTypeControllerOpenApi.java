package com.azzolim.renan.dvc.manager.api.v1.controller.openapi;

import com.azzolim.renan.dvc.manager.api.exceptionhandler.Problem;
import com.azzolim.renan.dvc.manager.api.v1.controller.in.DeviceTypeInput;
import com.azzolim.renan.dvc.manager.api.v1.controller.out.DeviceTypeOutput;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.List;

@Api(tags = "Device Types")
public interface DeviceTypeControllerOpenApi {

    @ApiOperation("Register a device type")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Device type registered"),
            @ApiResponse(code = 409, message = "A record already exists with same values")
    })
    DeviceTypeOutput save(
            @ApiParam(name = "body", value = "Representation of a device type", required = true)
            DeviceTypeInput input);


    @ApiOperation("Presents all device types.")
    List<DeviceTypeOutput> findAll();

    @ApiOperation("Searches a device type by its id.")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Invalid device type id", response = Problem.class),
            @ApiResponse(code = 404, message = "Device type not found", response = Problem.class)
    })
    DeviceTypeOutput findById(
            @ApiParam(value = "Device type id", example = "1", required = true) Long id
    );

    @ApiOperation("Deletes a device type by its id")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Device type removed"),
            @ApiResponse(code = 404, message = "Device type not found", response = Problem.class)
    })
    void delete(
            @ApiParam(value = "Device type id", example = "1", required = true) Long id
    );
}
