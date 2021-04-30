package com.example.buyingCurrencyService.dao;

import com.example.buyingCurrencyService.model.Account;
import com.example.buyingCurrencyService.model.Currency;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AccountRepositoryTest {

    @Autowired
    private AccountRepository accountRepository;

    @Test
    void checkByLoginIfAccountExists_success_test() {
        Account account = new Account("test", 1000, List.of(
                new Currency("USD", 150),
                new Currency("EUR", 150),
                new Currency("AED", 150)
        ));
        accountRepository.save(account);
        Account receivedAccount = accountRepository.findByLogin("test");
        assertEquals(receivedAccount.getLogin(), "test");
    }

    @Test
    void checkIfAccountDoesNotExistsReturnNull_fail_test() {
        Account receivedAccount = accountRepository.findByLogin("thisLoginDoesNotExists");
        assertNull(receivedAccount);
    }
}