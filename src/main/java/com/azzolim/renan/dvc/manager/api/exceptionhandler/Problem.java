package com.azzolim.renan.dvc.manager.api.exceptionhandler;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

import java.time.OffsetDateTime;
import java.util.List;

@ApiModel("Problem")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Builder
public class Problem {

    @ApiModelProperty(example = "400", position = 1)
    private Integer status;

    @ApiModelProperty(example = "Invalid data", position = 5)
    private String title;

    @ApiModelProperty(example = "One or more fields are incorrect. Fill it correctly and try again.", position = 10)
    private String detail;

    @ApiModelProperty(example = "2022-08-29T18:09:02.70844Z", position = 20)
    private OffsetDateTime timestamp;

    @ApiModelProperty(value = "List of objects or fields that caused the error (optional)", position = 25)
    private List<Object> objects;

    @ApiModel("ProblemObject")
    @Getter
    @Builder
    public static class Object {

        @ApiModelProperty(example = "amount")
        private String name;

        @ApiModelProperty(example = "Amount is mandatory")
        private String userMessage;

    }
}
