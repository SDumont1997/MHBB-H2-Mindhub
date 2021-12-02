package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.AccountType;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class AccountRestController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private PasswordEncoder passwordEncoder;

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
        Set<Account> accounts = clientService.getByEmail(authentication.getName()).getAccounts();
        List<Account> activeAccounts = accounts.stream().filter(account -> !account.isDisabled()).collect(toList());
        return activeAccounts.stream().map(AccountDTO::new).collect(toList());
    }

    @GetMapping("/clients/current/accounts/{id}")
    public AccountDTO getClientAccount(Authentication authentication, @PathVariable Long id){
        List<Account> retrievedAccounts = clientService.getByEmail(authentication.getName()).getAccounts().stream().collect(toList());
        return new AccountDTO(retrievedAccounts.stream().filter(account -> account.getId().equals(id)).findFirst().get());
    }

    public int getRandomNumber(int min, int max){
        return (int) ((Math.random()* (max - min)) + min);
    }

    @PostMapping("/clients/current/accounts")
    public ResponseEntity<Object> registerAccount(Authentication authentication, @RequestParam AccountType type){
        if (clientService.getByEmail(authentication.getName()).getAccounts().stream().filter(account -> !account.isDisabled()).collect(toList()).size() >= 3){
            return new ResponseEntity<>("Maximum number of accounts reached", HttpStatus.FORBIDDEN);
        }
        String accountNumber = "MHB-" + getRandomNumber(1, 99999999);
        while(accountService.getByNumber(accountNumber) != null){
            accountNumber = "MHB-" + getRandomNumber(1, 99999999);
        }
        accountService.save(new Account(clientService.getByEmail(authentication.getName()), accountNumber, 0, type));

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Transactional
    @PostMapping("/clients/current/accounts/disable")
    public ResponseEntity<Object> disableAccount(Authentication authentication, @RequestParam String accountNumber, @RequestParam String password){
        Client client = clientService.getByEmail(authentication.getName());
        Account account = accountService.getByNumber(accountNumber);
        if (accountNumber.isEmpty() || password.isEmpty()){
            return new ResponseEntity<>("Missing parameters", HttpStatus.BAD_REQUEST);
        }
        if (account == null){
            return new ResponseEntity<>("Account does not exist", HttpStatus.FORBIDDEN);
        }
        if (!passwordEncoder.matches(password, client.getPassword())){
            return new ResponseEntity<>("Incorrect password", HttpStatus.FORBIDDEN);
        }
        if (!client.getAccounts().contains(account)){
            return new ResponseEntity<>("Account does not belong to current user", HttpStatus.FORBIDDEN);
        }
        if (account.isDisabled()){
            return new ResponseEntity<>("Account is already disabled", HttpStatus.FORBIDDEN);
        }
        account.setDisabled(true);
        accountService.save(account);

        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }


}
