package com.example.buyingCurrencyService.security;

import com.example.buyingCurrencyService.model.entity.User;

import java.util.Optional;

public interface UserService {

    Optional<User> getUserByLogin(String login);
}
