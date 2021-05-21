package com.example.buyingCurrencyService.security;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.example.buyingCurrencyService.handlers.exception.ConnectionToS3Exception;
import com.example.buyingCurrencyService.model.entity.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@NoArgsConstructor
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserDaoIS3mpl implements UserDao{

    private @NonNull AmazonS3 amazonS3BucketClient;
    private @NonNull ObjectMapper objectMapper;

    @Override
    public Optional<User> getUserByLogin(String login) {

        S3Object s3object = amazonS3BucketClient.getObject("my-first-bucket-a", "users.json");
        try (S3ObjectInputStream inputStream = s3object.getObjectContent()) {
           List<User> users = objectMapper.readValue(inputStream, new TypeReference<>(){});

           return users.stream().filter(u -> u.getLogin().equalsIgnoreCase(login)).findAny();
        } catch (Exception e) {
            throw new ConnectionToS3Exception("Internal server error");
        }
    }
}