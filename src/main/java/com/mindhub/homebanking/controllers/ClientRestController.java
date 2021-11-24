package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class ClientRestController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/clients")
    public List<ClientDTO> getClients(){
        return clientService.getAll().stream().map(ClientDTO::new).collect(toList());
    }

    public int getRandomNumber(int min, int max){
        return (int) ((Math.random()* (max - min)) + min);
    }

    @PostMapping("/clients")
    public ResponseEntity<Object> registerClient(@RequestParam String firstName, @RequestParam String lastName, @RequestParam String email, @RequestParam String password){
        if(firstName.isEmpty()||lastName.isEmpty()||email.isEmpty()||password.isEmpty()){
            return new ResponseEntity<>("Missing Data", HttpStatus.FORBIDDEN);
        }

        if(clientService.getByEmail(email) != null){
            return new ResponseEntity<>("Email already in use", HttpStatus.FORBIDDEN);
        }

        Client newClient = new Client(firstName, lastName, email, passwordEncoder.encode(password));
        clientService.save(newClient);
        String accountNumber = "MHB-" + getRandomNumber(1, 99999999);
        while(accountService.getByNumber(accountNumber) != null){
            accountNumber = "MHB-" + getRandomNumber(1, 99999999);
        }
        accountService.save(new Account(newClient, accountNumber, 0));

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/clients/{id}")
    public ClientDTO getClient(@PathVariable Long id){
        return new ClientDTO(clientService.getById(id));
    }

    @GetMapping("/clients/current")
    public ClientDTO getCurrent(Authentication authentication){
        return new ClientDTO(clientService.getByEmail(authentication.getName()));
    };
}
