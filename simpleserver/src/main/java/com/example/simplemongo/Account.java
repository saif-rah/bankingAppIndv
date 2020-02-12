package com.example.simplemongo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
public class Account {
    @Id
    private String id;
    @Indexed(unique = true)
    private String username;
    private String password;
    private double balance;
    private boolean loginStatus;
    private List<Transaction> transactions;

    public Account() {
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

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("Customer_id "+ id + " User " + username + " balance " + balance +" loginStatus " + loginStatus);
        transactions.forEach((t) -> str.append(" source: "+t.getSource()+ " dest: "+t.getDestination()+ " amount: "+t.getAmount()));
        return str.toString();
    }
}