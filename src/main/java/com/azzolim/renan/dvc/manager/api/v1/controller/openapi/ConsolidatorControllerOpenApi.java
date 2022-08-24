package com.azzolim.renan.dvc.manager.api.v1.controller.openapi;

import com.azzolim.renan.dvc.manager.api.exceptionhandler.Problem;
import com.azzolim.renan.dvc.manager.api.v1.controller.out.ConsolidatorOutput;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Consolidator")
public interface ConsolidatorControllerOpenApi {

    @ApiOperation("Presents the monthly cost.")
    ConsolidatorOutput costsConsolidatorAll();

    @ApiOperation("Presents the monthly cost by device id.")
    @ApiResponses({
            @ApiResponse(code = 404, message = "Record not found.", response = Problem.class)
    })
    ConsolidatorOutput costsConsolidatorByDeviceId(
            @ApiParam(value = "Device id", example = "1", required = true)
            Long deviceId);
}
