package com.example.buyingCurrencyService.dao;

import com.example.buyingCurrencyService.model.entity.User;

import java.util.Optional;

public interface UserDao {

    Optional<User> getUser(String id);

    void addUser(User user);
}