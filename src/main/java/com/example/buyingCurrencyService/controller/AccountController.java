package com.example.buyingCurrencyService.controller;

import com.example.buyingCurrencyService.model.entity.Account;
import com.example.buyingCurrencyService.model.Currency;
import com.example.buyingCurrencyService.service.AccountService;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api")
@RestController
@NoArgsConstructor
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AccountController {

    private @NonNull AccountService accountService;

    @GetMapping("/account")
    public Account getAccount(Authentication authentication) {
        return accountService.getAccount(authentication.getName());
    }

    @GetMapping("/account/{currencyName}")
    public Account getAccountWithParticularCurrency(@PathVariable String currencyName, Authentication authentication) {
        return accountService
                .getAccountWithParticularCurrency(authentication.getName(), currencyName);
    }

    @PutMapping("/account")
    public Account updateAccount(@RequestBody Currency currency, Authentication authentication) {
        return accountService.updateAccount(authentication.getName(), currency);
    }
}