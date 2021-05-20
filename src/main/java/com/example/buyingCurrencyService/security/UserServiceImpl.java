package com.example.buyingCurrencyService.security;

import com.example.buyingCurrencyService.model.entity.User;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@NoArgsConstructor
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserServiceImpl implements UserService{

    private @NonNull UserDao userDaoS3Impl;

    public Optional<User> getUserByLogin(String login){

        return userDaoS3Impl.getUserByLogin(login);
    }
}