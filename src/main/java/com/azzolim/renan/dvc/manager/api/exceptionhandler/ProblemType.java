package com.azzolim.renan.dvc.manager.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {

    ALREADY_EXISTS("Record already exists."),
    NOT_FOUND("Record not found."),
    IN_USE("Record in use."),
    INVALID_DATA("Invalid data."),
    INVALID_PARAM("Param is invalid."),
    UNKNOWN_ERROR("Unknown error.");

    private String title;

    ProblemType(String title) {
        this.title = title;
    }
}
