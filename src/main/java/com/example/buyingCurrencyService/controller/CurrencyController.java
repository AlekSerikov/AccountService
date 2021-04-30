package com.example.buyingCurrencyService.controller;

import com.example.buyingCurrencyService.model.entity.Account;
import com.example.buyingCurrencyService.model.Currency;
import com.example.buyingCurrencyService.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api")
@RestController
public class CurrencyController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/account")
    public Account getAccountInfo(Authentication authentication) {
        return accountService.getAccount(authentication.getName());
    }

    @GetMapping("/account/{currencyName}")
    public Account getParticularCurrency(@PathVariable String currencyName, Authentication authentication) {
        return accountService
                .getAccountWithParticularCurrency(authentication.getName(), currencyName);
    }

    @PutMapping("/account")
    public Account updateAccount(@RequestBody Currency currency, Authentication authentication) {
        return accountService.updateAccount(authentication.getName(), currency);
    }
}