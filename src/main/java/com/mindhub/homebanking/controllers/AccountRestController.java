package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class AccountRestController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private ClientService clientService;

    @GetMapping("/accounts")
    public List<AccountDTO> getAccounts(){
        return accountService.getAll().stream().map(AccountDTO::new).collect(toList());
    }

    @GetMapping("/accounts/{id}")
    public AccountDTO getAccount(@PathVariable Long id){
        return new AccountDTO(accountService.getById(id));
    }

    @GetMapping("/clients/current/accounts")
    public List<AccountDTO> getClientAccounts(Authentication authentication){
        return clientService.getByEmail(authentication.getName()).getAccounts().stream().map(AccountDTO::new).collect(toList());
    }

    @GetMapping("/clients/current/accounts/{id}")
    public AccountDTO getClientAccount(Authentication authentication, @PathVariable Long id) throws IllegalAccessException{
        List<Account> retrievedAccounts = clientService.getByEmail(authentication.getName()).getAccounts().stream().collect(toList());
        return new AccountDTO(retrievedAccounts.stream().filter(account -> account.getId() == id).findFirst().get());
    }

    public int getRandomNumber(int min, int max){
        return (int) ((Math.random()* (max - min)) + min);
    }

    @PostMapping("/clients/current/accounts")
    public ResponseEntity<Object> registerAccount(Authentication authentication){
        if (clientService.getByEmail(authentication.getName()).getAccounts().size() >= 3){
            return new ResponseEntity<>("Maximum number of accounts reached", HttpStatus.FORBIDDEN);
        }
        String accountNumber = "MHB-" + getRandomNumber(1, 99999999);
        while(accountService.getByNumber(accountNumber) != null){
            accountNumber = "MHB-" + getRandomNumber(1, 99999999);
        }
        accountService.save(new Account(clientService.getByEmail(authentication.getName()), accountNumber, 0));

        return new ResponseEntity<>(HttpStatus.CREATED);
    }


}
