package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.services.CardService;
import com.mindhub.homebanking.services.ClientService;
import com.mindhub.homebanking.utils.CardUtils;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api")
public class CardRestController {

    @Autowired
    private CardService cardService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @PostMapping("/clients/current/cards")
    public ResponseEntity<Object> registerCard(Authentication authentication, @RequestParam CardType cardType, @RequestParam CardColor cardColor){
        if(clientService.getByEmail(authentication.getName()).getCards(cardType).size() >= 3){
            return new ResponseEntity<>("Maximum number of this type of card reached", HttpStatus.FORBIDDEN);
        }

        String cardNumber = CardUtils.getCardNumber();
        Integer cvv = CardUtils.getCvv();
        LocalDate from = LocalDate.now();
        LocalDate thru = LocalDate.now().plusYears(5);
        cardService.save(new Card(clientService.getByEmail(authentication.getName()), cardType, cardColor, cardNumber, cvv, from, thru));

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/clients/current/cards/delete")
    public ResponseEntity<Object> deleteCard(Authentication authentication, @RequestParam String cardNumber, @RequestParam String password){
        if (cardNumber.isEmpty() || password.isEmpty()){
            return new ResponseEntity<>("Missing parameters", HttpStatus.BAD_REQUEST);
        }
        if (cardService.getByNumber(cardNumber) == null){
            return new ResponseEntity<>("Card does not exist", HttpStatus.FORBIDDEN);
        }
        if (!cardService.getByNumber(cardNumber).getCardholder().equals(clientService.getByEmail(authentication.getName()))){
            return new ResponseEntity<>("Card does not belong to current user", HttpStatus.FORBIDDEN);
        }
        if (!passwordEncoder.matches(password, clientService.getByEmail(authentication.getName()).getPassword())){
            return new ResponseEntity<>("Incorrect password", HttpStatus.FORBIDDEN);
        }

        cardService.delete(cardService.getByNumber(cardNumber));

        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }


}
