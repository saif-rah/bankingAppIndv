package com.example.simpleclient;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

public class Account {
    private String id;
    private String username;
    private String source;
    private String destination;
    private String response;
    private double amount;
    private String password;
    private double balance;
    private boolean loginStatus;
    private List<Transaction> transactions;

    public Account() {
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Account(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Account(String id, String username, String source, String destination, double amount, String password, double balance, boolean loginStatus, List<Transaction> transactions) {
        this.id = id;
        this.username = username;
        this.source = source;
        this.destination = destination;
        this.amount = amount;
        this.password = password;
        this.balance = balance;
        this.loginStatus = loginStatus;
        this.transactions = transactions;
    }

    //List<Transaction> transactions
    public Account(String username, String password, double balance, List<Transaction> transactions) {
        this.username = username;
        this.password = password;
        this.balance = balance;
        this.loginStatus = false;
        this.transactions = transactions;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isLoginStatus() {
        return loginStatus;
    }

    public void setLoginStatus(boolean loginStatus) {
        this.loginStatus = loginStatus;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

}