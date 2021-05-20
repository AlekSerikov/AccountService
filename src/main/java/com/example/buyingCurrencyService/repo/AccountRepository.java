package com.example.buyingCurrencyService.repo;

import com.example.buyingCurrencyService.model.entity.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends CrudRepository <Account, String> {

    Account findByLogin(String login);
}