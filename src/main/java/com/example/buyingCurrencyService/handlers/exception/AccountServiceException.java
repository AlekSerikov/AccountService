package com.example.buyingCurrencyService.handlers.exception;

public class AccountServiceException extends RuntimeException{

    public AccountServiceException() {
    }

    public AccountServiceException(String message) {
        super(message);
    }

    public AccountServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public AccountServiceException(Throwable cause) {
        super(cause);
    }
}