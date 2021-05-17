package com.example.buyingCurrencyService.dao;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.example.buyingCurrencyService.model.entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AccountDaoImpl implements AccountDao{

    @Autowired
    private DynamoDBMapper dbMapper;

    @Override
    public Account getAccount(String id) {
        return dbMapper.load(Account.class, id);
    }

    @Override
    public void addAccount(Account account) {
        dbMapper.save(account);
    }

    @Override
    public Account updateAccount(Account account) {
        addAccount(account);
        return getAccount(account.getLogin());
    }
}