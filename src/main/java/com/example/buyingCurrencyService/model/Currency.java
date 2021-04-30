package com.example.buyingCurrencyService.model;

import org.springframework.data.annotation.Id;

import java.util.Objects;

public class Currency {

    private String name;
    private double value;

    public Currency() {
    }

    public Currency(String name, double value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Currency{" +
                "name='" + name + '\'' +
                ", value=" + value +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Currency currency = (Currency) o;
        return Double.compare(currency.value, value) == 0 && Objects.equals(name, currency.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, value);
    }
}