package com.azzolim.renan.dvc.manager.api.v1.controller.openapi;

import com.azzolim.renan.dvc.manager.api.exceptionhandler.Problem;
import com.azzolim.renan.dvc.manager.api.v1.controller.in.ServiceCostInput;
import com.azzolim.renan.dvc.manager.api.v1.controller.out.ServiceCostOutput;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.List;

@Api(tags = "Service Costs")
public interface ServiceCostControllerOpenApi {

    @ApiOperation("Register a service cost")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Service cost registered"),
            @ApiResponse(code = 409, message = "A record already exists with same values")
    })
    ServiceCostOutput save(
            @ApiParam(name = "body", value = "Representation of a service cost", required = true) ServiceCostInput input
    );

    @ApiOperation("Returns all available services costs.")
    List<ServiceCostOutput> findAll();

    @ApiOperation("Searches a service cost by its id.")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Invalid service cost id", response = Problem.class),
            @ApiResponse(code = 404, message = "Service cost not found", response = Problem.class)
    })
    ServiceCostOutput findById(
            @ApiParam(value = "Service cost id", example = "1", required = true) Long id
    );

    @ApiResponses({
            @ApiResponse(code = 204, message = "Service cost removed"),
            @ApiResponse(code = 404, message = "Service cost not found", response = Problem.class)
    })
    void delete(
            @ApiParam(value = "Service cost id", example = "1", required = true) Long id
    );
}
