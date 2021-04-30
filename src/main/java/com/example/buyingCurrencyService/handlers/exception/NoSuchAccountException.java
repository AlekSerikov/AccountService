package com.example.buyingCurrencyService.handlers.exception;

public class NoSuchAccountException extends AccountServiceException {

    public NoSuchAccountException() {
    }

    public NoSuchAccountException(String message) {
        super(message);
    }

    public NoSuchAccountException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSuchAccountException(Throwable cause) {
        super(cause);
    }
}
