package com.example.buyingCurrencyService;

import com.example.buyingCurrencyService.dao.AccountRepository;
import com.example.buyingCurrencyService.model.Currency;
import com.example.buyingCurrencyService.model.Role;
import com.example.buyingCurrencyService.model.Status;
import com.example.buyingCurrencyService.model.entity.Account;
import com.example.buyingCurrencyService.model.entity.User;
import com.example.buyingCurrencyService.security.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class BuyingCurrencyServiceApplication implements CommandLineRunner {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    UserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(BuyingCurrencyServiceApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        accountRepository.deleteAll();

        accountRepository.save(new Account("admin", new Currency ("BYN", 1000.0), List.of(
                new Currency("USD", 0),
                new Currency("EUR", 150),
                new Currency("AED", 150)
        )));
        accountRepository.save(new Account("user", new Currency ("BYN", 2000.0), List.of(
                new Currency("USD", 150),
                new Currency("EUR", 160)
        )));

        userRepository.deleteAll();

        userRepository.save(new User("admin", "$2y$12$xTXPgQU5x/Q//l/HtSyiYedlMYzPiWTHWN5GLjt/TtvdoIK5TnFLG", Role.ADMIN, Status.ACTIVE));
        userRepository.save(new User("user", "$2y$12$iZczeJ8FohLQ3iYGgvetF.YRJtmAOoSA0BsUe9doyq4drsSxHtsE6", Role.USER, Status.ACTIVE));
    }
}