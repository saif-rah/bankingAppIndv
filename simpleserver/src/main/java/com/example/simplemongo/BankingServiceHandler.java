package com.example.simplemongo;

import com.mongodb.DuplicateKeyException;
import com.mongodb.MongoException;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Null;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class BankingServiceHandler implements BankingService.Iface {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public String loginUser(String username, String password) throws TException {
        System.out.println("Login Called...");
        try{
            Account user = accountRepository.findByUsername(username);
            if(password.equals(user.getPassword())){
                user.setLoginStatus(true);
                accountRepository.save(user);
                System.out.println("Logged in user: ");
                System.out.println(accountRepository.findByUsername(username));
            }
            else{
                System.out.println("Wrong password.");
                return "wrong password";
            }
        }
        catch (NullPointerException np){
            return "null";
        }
        catch (Exception ex){
            System.out.println("....login exception...."+ex);
            return "exception";
        }
        return "success";
    }

    @Override
    public boolean logoutUser(String username) throws TException {
        try{
            //check if the user is logged in
            Account user = accountRepository.findByUsername(username);
            if(user.isLoginStatus()){
                user.setLoginStatus(false);
                accountRepository.save(user);
                System.out.println("User logged out ");
                System.out.println(accountRepository.findByUsername(username));
            }
            else{
                System.out.println("User not logged in");
                return false;
            }
        }
        catch (Exception ex){
            System.out.println("...logout exception..."+ex);
            return false;
        }
        return true;
    }

    @Override
    public String createAccount(String name, String pass) throws TException {
        try{
            List<Transaction> transactions = new ArrayList<Transaction>();
                    //Arrays.asList(new Transaction());
            accountRepository.insert(new Account(name, pass, 500.0, transactions));
        }
        catch (Exception ex){
            if(ex instanceof DuplicateKeyException) System.out.println("DupKey..." + ex);
            System.out.println("create account exception......."+ ex);
            return "exception: user already exists";
        }
        return "success";
    }

    @Override
    public String sendMoney(String source, String destination, double amount) throws TException {
        try{
            //check if logged in
            Account sourceUser = accountRepository.findByUsername(source);
            Account destinationUser = accountRepository.findByUsername(destination);
            if(sourceUser.isLoginStatus() && destinationUser!=null){
                //check if balance >= amount
                if(sourceUser.getBalance()>=amount){
                    //credit to dest & debit from source
                    //add transaction information
                    List<Transaction> sourceTransactions = sourceUser.getTransactions();
                    List<Transaction> destinationTransactions = destinationUser.getTransactions();

                    sourceTransactions.add(new Transaction(source, destination, amount));
                    destinationTransactions.add(new Transaction(source, destination, amount));

                    sourceUser.setBalance(sourceUser.getBalance()-amount);
                    sourceUser.setTransactions(sourceTransactions);
                    destinationUser.setBalance(destinationUser.getBalance()+amount);
                    destinationUser.setTransactions(destinationTransactions);

                    accountRepository.save(sourceUser);
                    accountRepository.save(destinationUser);
                    System.out.println("Money sent");
                }else{
                    System.out.println("Insufficient balance");
                    return "Insufficient Balance";
                }
            }
            else{
                System.out.println("User not logged in OR destination user not found");
                return "User not logged in OR destination user not found";
            }
        }
        catch (Exception ex){
            System.out.println("....send money exception...."+ex);
            return "exception. Not sent.";
        }
        return "success";
    }

    @Override
    public List<Transaction> getAllTransactions(String username) throws TException {
        Account user = accountRepository.findByUsername(username);
        return user.getTransactions();
    }
}