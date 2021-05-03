package com.example.buyingCurrencyService.model;

public enum Permission {
    GET_ACCOUNT_CURRENCIES("account:getCurrencies"),
    GET_ACCOUNT_CURRENCY("account:getCurrency"),
    UPDATE_ACCOUNT("update:updateAccount");

    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}