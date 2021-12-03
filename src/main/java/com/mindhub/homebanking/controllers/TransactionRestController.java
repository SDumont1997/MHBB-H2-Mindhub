package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientService;
import com.mindhub.homebanking.services.FrequentAccountService;
import com.mindhub.homebanking.services.TransactionService;
import com.mindhub.homebanking.utils.TransactionsExporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class TransactionRestController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private FrequentAccountService frequentAccountService;

    @Autowired
    private TransactionsExporter exporter;

    @Transactional
    @PostMapping("/transactions")
    public ResponseEntity<Object> makeTransaction(Authentication authentication, @RequestParam String originAccountNumber, @RequestParam String destinationAccountNumber, @RequestParam Double amount, @RequestParam String detail) {
        if (originAccountNumber.isEmpty() || destinationAccountNumber.isEmpty()) {
            return new ResponseEntity<>("Missing parameters", HttpStatus.FORBIDDEN);
        }
        if (originAccountNumber.equals(destinationAccountNumber)) {
            return new ResponseEntity<>("Origin and Destination accounts cannot have the same number", HttpStatus.FORBIDDEN);
        }
        if (accountService.getByNumber(originAccountNumber) == null) {
            return new ResponseEntity<>("Not an existing account", HttpStatus.FORBIDDEN);
        }
        if (!clientService.getByEmail(authentication.getName()).getAccounts().contains(accountService.getByNumber(originAccountNumber))) {
            return new ResponseEntity<>("Account does not belong to current client", HttpStatus.FORBIDDEN);
        }
        if (accountService.getByNumber(destinationAccountNumber) == null) {
            return new ResponseEntity<>("Destination account does not exist", HttpStatus.FORBIDDEN);
        }
        if (amount <= 0) {
            return new ResponseEntity<>("Transaction amount must be higher than zero", HttpStatus.FORBIDDEN);
        }
        if (accountService.getByNumber(originAccountNumber).getBalance() < amount) {
            return new ResponseEntity<>("Insufficient balance", HttpStatus.FORBIDDEN);
        }
        Account originAccount = accountService.getByNumber(originAccountNumber);
        Account destinationAccount = accountService.getByNumber(destinationAccountNumber);
        Client originClient = clientService.getByEmail(authentication.getName());
        Client destinationClient = destinationAccount.getOwner();

        if (detail.isEmpty()) {
            transactionService.save(new Transaction(originAccount, TransactionType.DEBIT, amount, destinationAccountNumber + " - " + destinationClient.getFirstName() + " " + destinationClient.getLastName()));
            transactionService.save(new Transaction(destinationAccount, TransactionType.CREDIT, amount, originAccountNumber + " - " + originClient.getFirstName() + " " + originClient.getLastName()));

            originAccount.setBalance(originAccount.getBalance() - amount);
            accountService.save(originAccount);
            destinationAccount.setBalance(destinationAccount.getBalance() + amount);
            accountService.save(destinationAccount);
            if (!originClient.getAccounts().contains(destinationAccount) && !originClient.getFrequentAccounts().stream().anyMatch(frequentAccount -> frequentAccount.getAccountNumber().equals(destinationAccountNumber))) {
                frequentAccountService.save(new FrequentAccount(destinationAccountNumber, destinationClient.getFirstName() + " " + destinationClient.getLastName(), originClient));
            }

            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        transactionService.save(new Transaction(originAccount, TransactionType.DEBIT, amount, destinationAccountNumber + " - " + destinationClient.getFirstName() + " " + destinationClient.getLastName(), detail));
        transactionService.save(new Transaction(destinationAccount, TransactionType.CREDIT, amount, originAccountNumber + " - " + originClient.getFirstName() + " " + originClient.getLastName(), detail));

        originAccount.setBalance(originAccount.getBalance() - amount);
        accountService.save(originAccount);
        destinationAccount.setBalance(destinationAccount.getBalance() + amount);
        accountService.save(destinationAccount);
        if (!originClient.getAccounts().contains(destinationAccount) && !originClient.getFrequentAccounts().stream().anyMatch(frequentAccount -> frequentAccount.getAccountNumber().equals(destinationAccountNumber))) {
            frequentAccountService.save(new FrequentAccount(destinationAccountNumber, destinationClient.getFirstName() + " " + destinationClient.getLastName(), originClient));
        }
        return new ResponseEntity<>(HttpStatus.CREATED);

    }

    @PostMapping("/transactions/export/pdf")
    public ResponseEntity<Object> exportTransactionsPdf(HttpServletResponse response, Authentication authentication, @RequestParam Long id) throws IOException{
        List<Transaction> transactions = accountService.getById(id).getTransactions().stream().sorted(Comparator.comparing(Transaction::getId)).collect(toList());
        Client client = clientService.getByEmail(authentication.getName());
        Account account = accountService.getById(id);
        exporter.exportToPDF(transactions, client, account, response);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
