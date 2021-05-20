package com.example.buyingCurrencyService.handlers.exception;

public class ConnectionToS3Exception extends AccountServiceException{

    public ConnectionToS3Exception() {
    }

    public ConnectionToS3Exception(String message) {
        super(message);
    }

    public ConnectionToS3Exception(String message, Throwable cause) {
        super(message, cause);
    }

    public ConnectionToS3Exception(Throwable cause) {
        super(cause);
    }
}
