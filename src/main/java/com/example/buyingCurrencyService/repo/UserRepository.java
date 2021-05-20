package com.example.buyingCurrencyService.repo;

import com.example.buyingCurrencyService.model.entity.User;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository <User, String> {

    Optional<User> findByLogin (String login);

    @EnableScan
    List<User> findAll();
}