package com.azzolim.renan.dvc.manager.api.v1.controller.openapi;


import com.azzolim.renan.dvc.manager.api.exceptionhandler.Problem;
import com.azzolim.renan.dvc.manager.api.v1.controller.in.ServiceInput;
import com.azzolim.renan.dvc.manager.api.v1.controller.out.ServiceOutput;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.List;

@Api(tags = "Services")
public interface ServiceControllerOpenApi {

    @ApiOperation("Register a service")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Service registered"),
            @ApiResponse(code = 409, message = "A record already exists with same values")
    })
    ServiceOutput save(
            @ApiParam(name = "body", value = "Representation of a service", required = true) ServiceInput input
    );

    @ApiResponses({
            @ApiResponse(code = 204, message = "Service removed"),
            @ApiResponse(code = 404, message = "Service not found", response = Problem.class)
    })
    void delete(
            @ApiParam(value = "Service id", example = "1", required = true) Long id
    );

    @ApiOperation("Searches a service by its id.")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Invalid service id", response = Problem.class),
            @ApiResponse(code = 404, message = "Service not found", response = Problem.class)
    })
    ServiceOutput findById(
            @ApiParam(value = "Service id", example = "1", required = true) Long id
    );

    @ApiOperation("Returns all available services.")
    List<ServiceOutput> findAll();
}
