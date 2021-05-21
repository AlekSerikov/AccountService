package com.example.buyingCurrencyService.controller;

import com.example.buyingCurrencyService.model.entity.Account;
import com.example.buyingCurrencyService.model.Currency;
import com.example.buyingCurrencyService.service.AccountService;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api")
@RestController
@NoArgsConstructor
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AccountController {

    private @NonNull AccountService accountService;

    @PreAuthorize("hasAnyAuthority('account:getCurrencies')")
    @GetMapping("/account")
    public Account getAccount(Authentication authentication) {
        return accountService.getAccount(authentication.getName());
    }

    @PreAuthorize("hasAnyAuthority('account:getCurrency')")
    @GetMapping("/account/{currencyName}")
    public Account getAccountWithParticularCurrency(@PathVariable String currencyName, Authentication authentication) {
        return accountService
                .getAccountWithParticularCurrency(authentication.getName(), currencyName);
    }

    @PreAuthorize("hasAnyAuthority('update:updateAccount')")
    @PutMapping("/account")
    public Account updateAccount(@RequestBody Currency currency, Authentication authentication) {
        return accountService.updateAccount(authentication.getName(), currency);
    }
}