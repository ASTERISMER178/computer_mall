package com.cy.store.service.ex;

import org.springframework.stereotype.Service;

public class getNameByCodeException extends ServiceException {
    public getNameByCodeException() {
        super();
    }

    public getNameByCodeException(String message) {
        super(message);
    }

    public getNameByCodeException(String message, Throwable cause) {
        super(message, cause);
    }

    public getNameByCodeException(Throwable cause) {
        super(cause);
    }

    protected getNameByCodeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
