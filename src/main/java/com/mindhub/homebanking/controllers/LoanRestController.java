package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.LoanApplicationDTO;
import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import com.mindhub.homebanking.services.*;
import org.hibernate.mapping.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

@RestController
@RequestMapping("/api")
public class LoanRestController {

    @Autowired
    private LoanService loanService;

    @Autowired
    private ClientLoanService clientLoanService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/loans")
    public List<LoanDTO> getLoans(){
        return loanService.getAll().stream().map(LoanDTO::new).collect(toList());
    }

    @Transactional
    @PostMapping("/loans")
    public ResponseEntity<Object> addLoan(Authentication authentication, @RequestBody LoanApplicationDTO loanApplicationDTO){
        if(loanApplicationDTO.getLoanAmount() <= 0){
            return new ResponseEntity<>("Loan amount must be higher than zero", HttpStatus.FORBIDDEN);
        }
        if (loanApplicationDTO.getLoanId() == null || loanApplicationDTO.getPayments() == null || loanApplicationDTO.getDestinationAccountNumber().equals("")){
            return new ResponseEntity<>("Missing parameters", HttpStatus.FORBIDDEN);
        }
        if(loanService.getById(loanApplicationDTO.getLoanId()) == null){
            return new ResponseEntity<>("Not a valid type of loan", HttpStatus.FORBIDDEN);
        }
        if(!loanService.getById(loanApplicationDTO.getLoanId()).getPayments().contains(loanApplicationDTO.getPayments())){
            return new ResponseEntity<>("Amount of payments not available", HttpStatus.FORBIDDEN);
        }
        if (accountService.getByNumber(loanApplicationDTO.getDestinationAccountNumber()) == null){
            return new ResponseEntity<>("Account does not exist", HttpStatus.FORBIDDEN);
        }
        if(loanService.getById(loanApplicationDTO.getLoanId()).getClientLoans().stream().map(clientLoan -> clientLoan.getClient()).collect(toSet()).contains(clientService.getByEmail(authentication.getName()))){
            return new ResponseEntity<>("Number of each type of loan per client cannot exceed one", HttpStatus.FORBIDDEN);
        }
        if (loanApplicationDTO.getLoanAmount() > loanService.getById(loanApplicationDTO.getLoanId()).getMaxAmount()){
            return new ResponseEntity<>("Maximum loan amount exceeded", HttpStatus.FORBIDDEN);
        }
        if (!accountService.getByNumber(loanApplicationDTO.getDestinationAccountNumber()).getOwner().equals(clientService.getByEmail(authentication.getName()))){
            return new ResponseEntity<>("Destination account does not belong to current user", HttpStatus.FORBIDDEN);
        }

        Client client = clientService.getByEmail(authentication.getName());
        Loan loan = loanService.getById(loanApplicationDTO.getLoanId());
        Account account = accountService.getByNumber(loanApplicationDTO.getDestinationAccountNumber());
        Double interests = loanApplicationDTO.getLoanAmount() / 100 * loan.getInterest();
        Double amount = loanApplicationDTO.getLoanAmount() + interests;
        clientLoanService.save(new ClientLoan(amount, loanApplicationDTO.getPayments(), client, loan));
        loanService.save(loan);
        transactionService.save(new Transaction(account, TransactionType.CREDIT, loanApplicationDTO.getLoanAmount(), "Mindhub Brothers Bank", "Loan approved"));
        account.setBalance(account.getBalance() + loanApplicationDTO.getLoanAmount());
        accountService.save(account);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/loans/addNew")
    public ResponseEntity<Object> addLoanType(@RequestParam String loanName, @RequestParam Double maxAmount, @RequestParam ArrayList<Integer> payments, @RequestParam Double interest){
        if (loanName.isEmpty() || payments.isEmpty()){
            return new ResponseEntity<>("Missing parameters", HttpStatus.BAD_REQUEST);
        }
        if (maxAmount < 1 || interest < 1 || payments.stream().anyMatch(integer -> integer < 1)){
            return new ResponseEntity<>("All numeric values must be one or higher", HttpStatus.FORBIDDEN);
        }
        loanService.save(new Loan(loanName, maxAmount, payments, interest));

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
