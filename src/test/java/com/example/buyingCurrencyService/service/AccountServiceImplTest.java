package com.example.buyingCurrencyService.service;

import com.example.buyingCurrencyService.handlers.exception.NoConnectionWithServiceException;
import com.example.buyingCurrencyService.handlers.exception.NoSuchAccountException;
import com.example.buyingCurrencyService.handlers.exception.NoSuchCurrencyInAccountException;
import com.example.buyingCurrencyService.handlers.exception.NotEnoughMoneyException;
import com.example.buyingCurrencyService.model.entity.Account;
import com.example.buyingCurrencyService.model.Currency;
import com.example.buyingCurrencyService.repo.AccountRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.NoFallbackAvailableException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ContextConfiguration(classes = {AccountServiceImpl.class})
@ExtendWith(SpringExtension.class)
class AccountServiceImplTest {

    //    @InjectMocks
    @Autowired
    AccountServiceImpl accountServiceImpl;

    @MockBean
    AccountRepository accountRepository;

    @MockBean
    CircuitBreaker circuitBreaker;

    @MockBean
    RestTemplate restTemplate;

//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.initMocks(this);
//    }

    @Test
    void getAccount_success_test() {
        Account account = new Account("admin", new Currency("BYN", 1000.0),
                List.of(new Currency("USD", 100.0)));

        when(accountRepository.findByLogin(any())).thenReturn(account);

        Account receivedAccount = accountServiceImpl.getAccount("admin");
        verify(accountRepository).findByLogin("admin");

        assertEquals(account, receivedAccount);
    }

    @ParameterizedTest(name = "{index}: ({0})")
    @MethodSource("localParameters")
    void getAccount_success_param_test(Account account) {
        when(accountRepository.findByLogin(any())).thenReturn(account);

        Account receivedAccount = accountServiceImpl.getAccount(account.getLogin());
        verify(accountRepository).findByLogin(account.getLogin());

        assertEquals(account, receivedAccount);
    }

    static Stream<Arguments> localParameters() {
        return Stream.of(
                Arguments.of(new Account("admin", new Currency("BYN", 1000.0),
                        List.of(new Currency("USD", 100.0)))),
                Arguments.of(new Account("user", new Currency("BYN", 1000.0),
                        List.of(new Currency("USD", 100.0))))
        );
    }

    @Test
    void getAccount_NoSuchAccountException_test() {
        when(accountRepository.findByLogin(any())).thenReturn(null);
        assertThrows(NoSuchAccountException.class,
                () -> accountServiceImpl.getAccount("noSuchUser"));
    }

    //-------------------------------------------------------

    @ParameterizedTest
    @ValueSource(strings = {"USD", "EUR", "RUB"})
    void getAccountWithParticularCurrency_success_test(String currencyName) {
        Account account = new Account("admin", new Currency("BYN", 1000.0),
                List.of(
                        new Currency("USD", 10.0),
                        new Currency("EUR", 10.0),
                        new Currency("RUB", 10.0)
                ));
        when(accountRepository.findByLogin(any())).thenReturn(account);

        Account accountWithParticularCurrency
                = accountServiceImpl.getAccountWithParticularCurrency("admin", currencyName);
        verify(accountRepository).findByLogin("admin");
        assertEquals(accountWithParticularCurrency.getCurrencies().size(), 1);
        assertEquals(currencyName, accountWithParticularCurrency.getCurrencies().get(0).getName());
    }

    @Test
    void getAccountWithParticularCurrency_NoSuchCurrencyInAccountException_test() {
        Account account = new Account("admin", new Currency("BYN", 1000.0),
                List.of(new Currency("USD", 10.0)));
        when(accountRepository.findByLogin("admin")).thenReturn(account);

        assertThrows(NoSuchCurrencyInAccountException.class,
                () -> accountServiceImpl.getAccountWithParticularCurrency(account.getLogin(), "NO")
        );
    }


    @Test
    void getAccountWithParticularCurrency_NoSuchUserAccountException_test() {
        when(accountRepository.findByLogin(any())).thenReturn(null);

        assertThrows(NoSuchAccountException.class,
                () -> accountServiceImpl.getAccountWithParticularCurrency("TestAccount", "USD")
        );
    }

    //------------------------------------

    @Test
    void updateAccount_success_test() {
        Account account = new Account("admin", new Currency("BYN", 1000.0),
                Arrays.asList(new Currency("USD", 100.0)));
        when(accountRepository.findByLogin("admin")).thenReturn(account);

        when(circuitBreaker.run(any())).thenReturn(new Currency("USD", 2.5));

        Currency currencyToBuy = new Currency("USD", 15.0);

        accountServiceImpl.updateAccount("admin", currencyToBuy);
        verify(accountRepository).findByLogin("admin");
        verify(accountRepository).save(account);
        Currency currencyFromMock = account.getCurrencies().stream()
                .filter(currency -> currency.getName().equals("USD"))
                .findAny().get();
        assertTrue(currencyFromMock.getName().equals("USD") && currencyFromMock.getValue() == 115.0);
    }

    @Test
    void updateAccount_NotEnoughMoneyException_Test() {
        Account account = new Account("admin", new Currency("BYN", 10.0),
                Arrays.asList(new Currency("USD", 100.0)));

        when(accountRepository.findByLogin(any())).thenReturn(account);

        when(circuitBreaker.run(any())).thenReturn(new Currency("USD", 2.5));

        Currency currencyToBuy = new Currency("USD", 15.0);

        assertThrows(NotEnoughMoneyException.class,
                () -> accountServiceImpl.updateAccount("admin", currencyToBuy));
    }

    @Test
    void updateAccount_NoConnectionWithServiceException_Test() {
        Account account = new Account("admin", new Currency("BYN", 10.0),
                Arrays.asList(new Currency("USD", 100.0)));

        when(accountRepository.findByLogin(any())).thenReturn(account);

        when(circuitBreaker.run(any())).thenThrow(new NoFallbackAvailableException("", new RuntimeException()));

        assertThrows(NoConnectionWithServiceException.class,
                () -> accountServiceImpl.updateAccount("admin", new Currency("USD", 15.0)));
    }
}