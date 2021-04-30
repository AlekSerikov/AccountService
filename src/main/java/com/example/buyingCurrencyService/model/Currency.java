package com.example.buyingCurrencyService.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Currency {
    private String name;
    private double value;
}