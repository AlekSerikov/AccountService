package com.example.buyingCurrencyService;


import com.example.buyingCurrencyService.model.entity.User;
import com.example.buyingCurrencyService.repo.AccountRepository;
import com.example.buyingCurrencyService.repo.UserRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;


@SpringBootApplication
public class BuyingCurrencyServiceApplication implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    ObjectMapper objectMapper;


    public static void main(String[] args) {
        SpringApplication.run(BuyingCurrencyServiceApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

//        accountDao.addAccount(new Account("admin", new Currency("BYN", 1000.0), List.of(
//                new Currency("USD", 0),
//                new Currency("EUR", 150),
//                new Currency("AED", 150)
//        )));
//        accountDao.addAccount(new Account("user", new Currency("BYN", 2000.0), List.of(
//                new Currency("USD", 150),
//                new Currency("EUR", 160)
//        )));

//        System.out.println(userRepository.findByLogin("admin"));


        String string = objectMapper.writeValueAsString(userRepository.findAll());
        System.out.println(string);

        String users = "[{\"login\":\"admin\",\"password\":\"$2y$12$xTXPgQU5x/Q//l/HtSyiYedlMYzPiWTHWN5GLjt/TtvdoIK5TnFLG\",\"role\":\"ADMIN\",\"status\":\"ACTIVE\"},{\"login\":\"user\",\"password\":\"$2y$12$iZczeJ8FohLQ3iYGgvetF.YRJtmAOoSA0BsUe9doyq4drsSxHtsE6\",\"role\":\"USER\",\"status\":\"ACTIVE\"}]";

        System.out.println(objectMapper.readValue(users, new TypeReference<List<User>>(){}));

//        System.out.println(accountRepository.findByLogin("admin"));

//        userDao.addUser(new User("admin", "$2y$12$xTXPgQU5x/Q//l/HtSyiYedlMYzPiWTHWN5GLjt/TtvdoIK5TnFLG", Role.ADMIN, Status.ACTIVE));
//        userDao.addUser(new User("user", "$2y$12$iZczeJ8FohLQ3iYGgvetF.YRJtmAOoSA0BsUe9doyq4drsSxHtsE6", Role.USER, Status.ACTIVE));
    }
}