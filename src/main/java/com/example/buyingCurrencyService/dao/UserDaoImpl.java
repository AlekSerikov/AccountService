package com.example.buyingCurrencyService.dao;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.example.buyingCurrencyService.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private DynamoDBMapper dbMapper;

    @Override
    public Optional<User> getUser(String id) {
        return Optional.of(dbMapper.load(User.class, id));
    }

    @Override
    public void addUser(User user) {
        dbMapper.save(user);
    }
}
