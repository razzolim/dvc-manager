package com.azzolim.renan.dvc.manager.domain.exception;

public class EntityAlreadyExistsException extends BusinessException{
    public EntityAlreadyExistsException(String msg) {
        super(msg);
    }
}
