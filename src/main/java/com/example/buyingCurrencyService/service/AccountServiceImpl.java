package com.example.buyingCurrencyService.service;

import com.example.buyingCurrencyService.dao.AccountRepository;
import com.example.buyingCurrencyService.handlers.exception.NoConnectionWithServiceException;
import com.example.buyingCurrencyService.handlers.exception.NoSuchCurrencyInAccountException;
import com.example.buyingCurrencyService.handlers.exception.NoSuchAccountException;
import com.example.buyingCurrencyService.handlers.exception.NotEnoughMoneyException;
import com.example.buyingCurrencyService.model.Currency;
import com.example.buyingCurrencyService.model.Account;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CircuitBreakerFactory cbFactory;

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${currencyRateServiceBasePartURL}")
    private String currencyRateServiceBasePartURL;

    private final static String CIRCUIT_BREAKER_ID = "accountService";


    @Override
    public Account getAccount(String login) {

        Account account = accountRepository.findByLogin(login);
        if (account == null) throw new NoSuchAccountException();
        return account;
    }


    @Override
    public Currency getParticularUserCurrencyAndCheckIfCurrencyPresent(String login, String currencyName) {
        return getParticularCurrencyFromAccount(accountRepository.findByLogin(login), currencyName)
                .orElseThrow(() -> new NoSuchCurrencyInAccountException("There is no currency with name " + currencyName));
    }

    @Override
    public Account updateAccount(String userName, Currency currency) {
        Account account = accountRepository.findByLogin(userName);
        Currency costOfOneUnitOfCurrencyInBYN = getCostOfOneUnitOfCurrencyInBYN(currency.getName());
        double theCostOfBuyingCurrencyInRubles = currency.getValue() * costOfOneUnitOfCurrencyInBYN.getValue();

        checkBalance(theCostOfBuyingCurrencyInRubles, account);

        addCurrencyIfAbsentInAccount(account, currency.getName());

        Currency currencyToUpdate = getParticularCurrencyFromAccount(account, currency.getName()).get();
        account.setBalance(account.getBalance() - theCostOfBuyingCurrencyInRubles);
        currencyToUpdate.setValue(currencyToUpdate.getValue() + currency.getValue());
        return accountRepository.save(account);
    }


    private void checkBalance(double currencyCost, Account account) {
        if (currencyCost > account.getBalance()) {
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
        CircuitBreaker accountServiceCb = cbFactory.create(CIRCUIT_BREAKER_ID);// вынести
        try {
            return accountServiceCb.run(()
                    -> restTemplate.getForObject(currencyRateServiceBasePartURL + currencyName, Currency.class));
        } catch (Exception e) {
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