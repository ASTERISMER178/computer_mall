package com.cy.store.service.ex;

public class AddNewAddressException extends ServiceException{
    public AddNewAddressException() {
        super();
    }

    public AddNewAddressException(String message) {
        super(message);
    }

    public AddNewAddressException(String message, Throwable cause) {
        super(message, cause);
    }

    public AddNewAddressException(Throwable cause) {
        super(cause);
    }

    protected AddNewAddressException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
