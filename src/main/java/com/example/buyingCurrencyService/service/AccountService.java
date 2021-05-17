package com.example.buyingCurrencyService.service;

import com.example.buyingCurrencyService.model.entity.Account;
import com.example.buyingCurrencyService.model.Currency;

public interface AccountService {

    Account getAccount(String login);

    Account getAccountWithParticularCurrency(String login, String currencyName);

    Account updateAccount(String userName, Currency currency);
}