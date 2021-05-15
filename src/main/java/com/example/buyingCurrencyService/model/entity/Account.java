package com.example.buyingCurrencyService.model.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.example.buyingCurrencyService.model.Currency;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@DynamoDBTable(tableName = "accounts")
public class Account implements Serializable {

    @DynamoDBHashKey(attributeName = "login")
    private String login;

    @DynamoDBAttribute
    private Currency balance;

    @DynamoDBAttribute
    private List<Currency> currencies;
}