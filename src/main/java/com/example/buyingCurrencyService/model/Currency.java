package com.example.buyingCurrencyService.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@DynamoDBDocument
public class Currency implements Serializable {

    @DynamoDBAttribute
    private String name;

    @DynamoDBAttribute
    private double value;
}