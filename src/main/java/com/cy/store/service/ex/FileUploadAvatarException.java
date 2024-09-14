package com.cy.store.service.ex;

public class FileUploadAvatarException extends ServiceException{
    public FileUploadAvatarException() {
        super();
    }

    public FileUploadAvatarException(String message) {
        super(message);
    }

    public FileUploadAvatarException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileUploadAvatarException(Throwable cause) {
        super(cause);
    }

    protected FileUploadAvatarException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
