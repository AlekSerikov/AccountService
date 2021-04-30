package com.example.buyingCurrencyService.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Objects;

@Document(collection = "currencyUsers")
public class Account {

    @Id
    private String login;
    private double balance;
    private List<Currency> currencies;

    public Account() {
    }

    public Account(String login, double balance, List<Currency> currencies) {
        this.login = login;
        this.balance = balance;
        this.currencies = currencies;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public List<Currency> getCurrencies() {
        return currencies;
    }

    public void setCurrencies(List<Currency> currencies) {
        this.currencies = currencies;
    }

    @Override
    public String toString() {
        return "User{" +
                "login='" + login + '\'' +
                ", balance=" + balance +
                ", wallet=" + currencies +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Double.compare(account.balance, balance) == 0 && Objects.equals(login, account.login) && Objects.equals(currencies, account.currencies);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, balance, currencies);
    }
}