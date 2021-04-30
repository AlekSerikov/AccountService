package com.example.buyingCurrencyService.handlers.exception;

public class BuyingCurrencyServiceException extends RuntimeException{

    public BuyingCurrencyServiceException() {
    }

    public BuyingCurrencyServiceException(String message) {
        super(message);
    }

    public BuyingCurrencyServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public BuyingCurrencyServiceException(Throwable cause) {
        super(cause);
    }
}