package com.example.buyingCurrencyService.dao;

import com.example.buyingCurrencyService.model.entity.Account;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AccountRepository extends MongoRepository<Account, String> {
    Account findByLogin(String login);
}