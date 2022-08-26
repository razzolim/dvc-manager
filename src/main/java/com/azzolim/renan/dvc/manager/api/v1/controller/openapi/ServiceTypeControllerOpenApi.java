package com.azzolim.renan.dvc.manager.api.v1.controller.openapi;

import com.azzolim.renan.dvc.manager.api.exceptionhandler.Problem;
import com.azzolim.renan.dvc.manager.api.v1.controller.in.ServiceTypeInput;
import com.azzolim.renan.dvc.manager.api.v1.controller.out.ServiceTypeOutput;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.List;


@Api(tags = "Service Types")
public interface ServiceTypeControllerOpenApi {

    @ApiOperation("Register a service type")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Service type registered"),
            @ApiResponse(code = 409, message = "A record already exists with same values")
    })
    ServiceTypeOutput save(
            @ApiParam(name = "body", value = "Representation of a service type", required = true) ServiceTypeInput input
    );

    @ApiOperation("Returns all available services types.")
    List<ServiceTypeOutput> findAll();

    @ApiOperation("Searches a service type by its id.")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Invalid service type id", response = Problem.class),
            @ApiResponse(code = 404, message = "Service cost not found", response = Problem.class)
    })
    ServiceTypeOutput findById(
            @ApiParam(value = "Service type id", example = "1", required = true) Long id
    );

    @ApiResponses({
            @ApiResponse(code = 204, message = "Service type removed"),
            @ApiResponse(code = 404, message = "Service type not found", response = Problem.class)
    })
    void delete(
            @ApiParam(value = "Service type id", example = "1", required = true) Long id
    );
}
