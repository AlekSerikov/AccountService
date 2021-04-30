package com.example.buyingCurrencyService.handlers.exception;

public class NoSuchCurrencyInAccountException extends BuyingCurrencyServiceException{

    public NoSuchCurrencyInAccountException() {
    }

    public NoSuchCurrencyInAccountException(String message) {
        super("There is no currency with name " + message);
    }

    public NoSuchCurrencyInAccountException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSuchCurrencyInAccountException(Throwable cause) {
        super(cause);
    }
}