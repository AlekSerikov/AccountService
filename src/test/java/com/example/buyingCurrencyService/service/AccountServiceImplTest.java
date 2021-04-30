package com.example.buyingCurrencyService.service;

import com.example.buyingCurrencyService.dao.AccountRepository;
import com.example.buyingCurrencyService.handlers.exception.NoSuchCurrencyInAccountException;
import com.example.buyingCurrencyService.model.entity.Account;
import com.example.buyingCurrencyService.model.Currency;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class AccountServiceImplTest {

    @Autowired
    AccountService accountService;
    @MockBean
    AccountRepository accountRepository;
//    private Account accountFromMock;

    @Test
    void getAccount_success_test() {
        Account account = new Account("admin", new Currency("BYN", 1000.0),
                Arrays.asList(new Currency("USD", 100.0)));
        when(accountRepository.findByLogin("admin")).thenReturn(account);

        Account receivedAccount = accountService.getAccount("admin");
        verify(accountRepository).findByLogin("admin");
        assertEquals(account, receivedAccount);
    }

//    @Test
//    void getAccount_NoSuchAccountException_test() {
//        when(accountRepository.findByLogin(any())).thenReturn(null);
//        assertThrows(NoSuchAccountException.class, () -> accountService.getAccount("noSuchUser"));
//    }
//
//    //-----
//
//    @Test()
//    void getParticularUserCurrencyAndCheckIfCurrencyPresent_success_test() {
//        Account account = new Account("admin", new Currency("BYN", 1000.0),
//                List.of(new Currency("USD", 10.0)));
//        when(accountRepository.findByLogin("admin")).thenReturn(account);
//        Currency currency
//                = accountService.getAccountWithParticularCurrency("admin", "USD");
//        verify(accountRepository).findByLogin("admin");
//        assertEquals("USD", currency.getName());
//    }

    @Test()
    void getParticularUserCurrencyAndCheckIfCurrencyPresent_NoSuchCurrencyInAccountException_test() {
        Account account = new Account("admin", new Currency("BYN", 1000.0),
                List.of(new Currency("USD", 10.0)));
        when(accountRepository.findByLogin("admin")).thenReturn(account);

        assertThrows(NoSuchCurrencyInAccountException.class,
                () -> accountService.getAccountWithParticularCurrency(account.getLogin(), "NO")
        );
    }

    //--

    @Test
    void updateAccount_success_test() {
        Account account = new Account("admin", new Currency("BYN", 1000.0),
                Arrays.asList(new Currency("USD", 100.0)));
        when(accountRepository.findByLogin("admin")).thenReturn(account);

        Currency currencyRate = new Currency("USD", 15.0);
        accountService.updateAccount("admin", currencyRate);
        verify(accountRepository).findByLogin("admin");
        verify(accountRepository).save(account);
        Currency currencyFromMock = account.getCurrencies().stream()
                .filter(currency -> currency.getName().equals("USD"))
                .findAny().get();
        assertTrue(currencyFromMock.getName().equals("USD") && currencyFromMock.getValue() == 115.0);
    }

    @Test
    void updateAccount_NotEnoughMoneyException_Test() {
        Account account = new Account("admin", new Currency("BYN", 1000.0),
                Arrays.asList(new Currency("USD", 100.0)));

        when(accountRepository.findByLogin("admin")).thenReturn(account);

    }


}