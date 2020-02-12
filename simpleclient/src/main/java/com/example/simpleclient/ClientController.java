package com.example.simpleclient;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TJSONProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.THttpClient;
import org.apache.thrift.transport.TTransportException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.swing.text.AbstractDocument;
import java.util.List;

@Controller
public class ClientController {

    THttpClient httpClient;

    {
        try {
            httpClient = new THttpClient("http://localhost:8080/banking");
        } catch (TTransportException e) {
            e.printStackTrace();
        }
    }

    // create protocol //Protocol layer
    TProtocol protocol = new TJSONProtocol(httpClient);
    //use RPC service
    BankingService.Client client = new BankingService.Client(protocol);

    @RequestMapping(value = "/")
    public String showSignUpForm() {
        return "add-user";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/create")
    public String addAccount(@ModelAttribute("account")Account account, Model model) throws TException {
//        System.out.println("username: "+ account.getUsername());
        String response = client.createAccount(account.getUsername(), account.getPassword());
        if(response.equals("success")){
            return "redirect:/login";
        }
        else {
            account.setResponse(response);
            model.addAttribute("account", account);
            return "failed";
        }
    }

    @RequestMapping(value = "/user")
    public String showUser(){
        return "bank-service";
    }

    @RequestMapping(value = "/login")
    public String showLogInForm() {
        return "login-user";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/login")
    public String login(@ModelAttribute("account")Account account, Model model) throws TException{
        System.out.println("username: "+ account.getUsername());
        String response = client.loginUser(account.getUsername(), account.getPassword());
        if(response.equals("success")){
            model.addAttribute("account", account);
            return "bank-service";
        }
        else {
            account.setResponse(response);
            model.addAttribute("account", account);
            return "failed";
        }
    }

    @RequestMapping(value = "/send")
    public String showSend(){
        return "show-send";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/send")
    public String send(@ModelAttribute("account")Account account, Model model) throws TException{
        String response = client.sendMoney(account.getSource(), account.getDestination(), account.getAmount());
        if(response.equals("success")){
            account.setUsername(account.getSource());
            model.addAttribute("account", account);
            return "bank-service";
        }
        else {
            account.setResponse(response);
            model.addAttribute("account", account);
            return "failed";
        }
    }

    @RequestMapping("/getall/{name}")
    public String getAll(@PathVariable String name, Model model) throws TException{
        List<Transaction> transactions =  client.getAllTransactions(name);
        model.addAttribute("transactions", transactions);
        return "show-transactions";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/logout")
    public String logout(@RequestParam(name="username") String username) throws TException{
        if(client.logoutUser(username)) return "logout-show";
        else return "failed";
    }
}
