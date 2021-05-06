package com.example.buyingCurrencyService.service;

import com.example.buyingCurrencyService.dao.AccountRepository;
import com.example.buyingCurrencyService.handlers.exception.NoConnectionWithServiceException;
import com.example.buyingCurrencyService.handlers.exception.NoSuchCurrencyInAccountException;
import com.example.buyingCurrencyService.handlers.exception.NoSuchAccountException;
import com.example.buyingCurrencyService.handlers.exception.NotEnoughMoneyException;
import com.example.buyingCurrencyService.model.Currency;
import com.example.buyingCurrencyService.model.entity.Account;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
@NoArgsConstructor
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AccountServiceImpl implements AccountService {

    @Value("${currencyRateServiceBasePartURL}")
    private String currencyRateServiceBasePartURL;
    private @NonNull AccountRepository accountRepository;
    private @NonNull RestTemplate restTemplate;
    private @NonNull CircuitBreaker circuitBreaker;

    @Override
    public Account getAccount(String login) {
        Account account = accountRepository.findByLogin(login);
        if (account == null) throw new NoSuchAccountException();
        return account;
    }

    @Override
    public Account getAccountWithParticularCurrency(String login, String currencyName) {
        Account account = getAccount(login);
        Currency particularCurrency = getParticularCurrencyFromAccount(account, currencyName)
                .orElseThrow(() -> new NoSuchCurrencyInAccountException("There is no currency with name " + currencyName));
        return new Account(account.getLogin(), account.getBalance(), List.of(particularCurrency));
    }

    @Override
    public Account updateAccount(String userName, Currency currency) {
        Account account = getAccount(userName);
        Currency costOfOneUnitOfCurrencyInBYN = getCostOfOneUnitOfCurrencyInBYN(currency.getName());
        double theCostOfBuyingCurrencyInRubles = currency.getValue() * costOfOneUnitOfCurrencyInBYN.getValue();
        checkBalance(theCostOfBuyingCurrencyInRubles, account);
        addCurrencyIfAbsentInAccount(account, currency.getName());
        Currency currencyToUpdate = getParticularCurrencyFromAccount(account, currency.getName()).get();
        account.getBalance().setValue(account.getBalance().getValue() - theCostOfBuyingCurrencyInRubles);
        currencyToUpdate.setValue(currencyToUpdate.getValue() + currency.getValue());
        return accountRepository.save(account);
    }

    private void checkBalance(double currencyCost, Account account) {
        if (currencyCost > account.getBalance().getValue()) {
            throw new NotEnoughMoneyException();
        }
    }

    private void addCurrencyIfAbsentInAccount(Account account, String currencyName) {
        if (isCurrencyPresentsInAccount(account, currencyName)) {
            account.getCurrencies().add(new Currency(currencyName, 0.0));
            accountRepository.save(account);
        }
    }

    private Currency getCostOfOneUnitOfCurrencyInBYN(String currencyName) {
        try {
            return circuitBreaker.run(()
                    -> restTemplate.getForObject(currencyRateServiceBasePartURL + currencyName, Currency.class));
        } catch (Exception e) {
            System.out.println(e.toString());
            throw new NoConnectionWithServiceException("Internal server error");
        }
    }

    private Optional<Currency> getParticularCurrencyFromAccount(Account account, String currencyName) {
        return account.getCurrencies().stream()
                .filter(c -> c.getName().equalsIgnoreCase(currencyName))
                .findAny();
    }

    private boolean isCurrencyPresentsInAccount(Account account, String currencyName) {
        return getParticularCurrencyFromAccount(account, currencyName).isEmpty();
    }
}