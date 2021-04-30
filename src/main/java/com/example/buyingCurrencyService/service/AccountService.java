package com.example.buyingCurrencyService.service;

import com.example.buyingCurrencyService.model.Account;
import com.example.buyingCurrencyService.model.Currency;

public interface AccountService {

    Account getAccount(String login);

    Currency getParticularUserCurrencyAndCheckIfCurrencyPresent(String login, String currencyName);

    Account updateAccount(String userName, Currency currency);
}