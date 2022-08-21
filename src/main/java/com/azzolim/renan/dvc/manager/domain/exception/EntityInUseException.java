package com.azzolim.renan.dvc.manager.domain.exception;

public class EntityInUseException extends BusinessException {
    public EntityInUseException(String msg) {
        super(msg);
    }
}
