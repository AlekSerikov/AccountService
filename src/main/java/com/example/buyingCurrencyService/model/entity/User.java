package com.example.buyingCurrencyService.model.entity;

import com.example.buyingCurrencyService.model.Role;
import com.example.buyingCurrencyService.model.Status;
import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@Document(collection = "users")
public class User {

    @Id
    private String login;
    private String password;
    private Role role;
    private Status status;
}
