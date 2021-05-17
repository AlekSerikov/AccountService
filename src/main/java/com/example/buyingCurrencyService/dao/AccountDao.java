package com.example.buyingCurrencyService.dao;

import com.example.buyingCurrencyService.model.entity.Account;

public interface AccountDao {

    Account getAccount(String id);

    void addAccount(Account account);

    Account updateAccount(Account account);
}