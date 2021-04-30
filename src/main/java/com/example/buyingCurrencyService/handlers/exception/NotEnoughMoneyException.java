package com.example.buyingCurrencyService.handlers.exception;

public class NotEnoughMoneyException extends AccountServiceException {

    private String message;

    public NotEnoughMoneyException() {
    }

    public NotEnoughMoneyException(String message) {
       this.message = message;
    }

    public NotEnoughMoneyException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotEnoughMoneyException(Throwable cause) {
        super(cause);
    }

    @Override
    public String getMessage() {
        if (this.message == null) return "Not enough money in the balance";
        return message;
    }
}