package com.example.buyingCurrencyService.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Permission {
    GET_ACCOUNT_CURRENCIES("account:getCurrencies"),
    GET_ACCOUNT_CURRENCY("account:getCurrency"),
    UPDATE_ACCOUNT("update:updateAccount");

    private final String permission;
}