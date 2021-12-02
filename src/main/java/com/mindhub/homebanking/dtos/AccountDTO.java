package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.AccountType;

import java.util.Set;

import static java.util.stream.Collectors.toSet;

public class AccountDTO {
    private Long id;
    private String number;
    private String creationDate;
    private double balance;
    private Set<TransactionDTO> transactions;
    private String type;
    private boolean disabled;

    public AccountDTO(){

    }

    public AccountDTO(Account account){
        this.id = account.getId();
        this.number = account.getNumber();
        this.creationDate = account.getCreationDate();
        this.balance = account.getBalance();
        this.transactions = account.getTransactions().stream().map(TransactionDTO::new).collect(toSet());
        this.type = account.getType().toString().substring(0, 1) + account.getType().toString().substring(1).toLowerCase();
        this.disabled = account.isDisabled();
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

    public String getType() {
        return type;
    }

    public void setType(AccountType type) {
        this.type = type.toString().substring(0, 1) + type.toString().substring(1).toLowerCase();;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AccountDTO{");
        sb.append("id=").append(id);
        sb.append(", number='").append(number).append('\'');
        sb.append(", type").append(type);
        sb.append(", creationDate=").append(creationDate);
        sb.append(", balance=").append(balance);
        sb.append(", transactions=").append(transactions);
        sb.append(", disabled=").append(disabled);
        sb.append('}');
        return sb.toString();
    }
}
