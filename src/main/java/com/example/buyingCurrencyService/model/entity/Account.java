package com.example.buyingCurrencyService.model.entity;

import com.example.buyingCurrencyService.model.Currency;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@AllArgsConstructor
@Document(collection = "accounts")
public class Account {

    @Id
    private String login;
    private Currency balance;
    private List<Currency> currencies;

}