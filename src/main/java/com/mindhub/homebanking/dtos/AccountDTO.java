package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Account;

import java.util.Set;

import static java.util.stream.Collectors.toSet;

public class AccountDTO {
    private Long id;
    private String number;
    private String creationDate;
    private double balance;
    private Set<TransactionDTO> transactions;

    public AccountDTO(){

    }

    public AccountDTO(Account account){
        this.id = account.getId();
        this.number = account.getNumber();
        this.creationDate = account.getCreationDate();
        this.balance = account.getBalance();
        this.transactions = account.getTransactions().stream().map(TransactionDTO::new).collect(toSet());
    }

    public Long getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Set<TransactionDTO> getTransactions(){
        return this.transactions;
    }

    public void setTransactions(Set<TransactionDTO> transactions){
        this.transactions = transactions;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AccountDTO{");
        sb.append("id=").append(id);
        sb.append(", number='").append(number).append('\'');
        sb.append(", creationDate=").append(creationDate);
        sb.append(", balance=").append(balance);
        sb.append(", transactions=").append(transactions);
        sb.append('}');
        return sb.toString();
    }
}
