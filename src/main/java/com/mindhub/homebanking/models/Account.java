package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;
    private String number;
    private String creationDate;
    private double balance;
    private AccountType type;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "owner_id")
    private Client owner;

    @OneToMany(mappedBy = "account", fetch = FetchType.EAGER)
    private Set<Transaction> transactions = new HashSet<>();

    @OneToMany(mappedBy = "account", fetch = FetchType.EAGER)
    private Set<Card> cards = new HashSet<>();

    private boolean disabled;

    public Account() {

    }

    public Account(Client owner, String number, double balance, AccountType type) {
        this.number = number;
        this.creationDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        this.balance = balance;
        this.type = type;
        this.disabled = false;
        owner.addAccount(this);
    }

    public Long getId(){
        return this.id;
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

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Client getOwner() {
        return owner;
    }

    public void setOwner(Client owner) {
        this.owner = owner;
    }

    public Set<Transaction> getTransactions() {
        return transactions;
    }

    public void addTransaction(Transaction transaction) {
        transaction.setAccount(this);
        transactions.add(transaction);
    }

    public AccountType getType() {
        return type;
    }

    public void setType(AccountType type) {
        this.type = type;
    }

    public Set<Card> getCards(){
        return this.cards;
    }

    public void addCard(Card card){
        this.cards.add(card);
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Account{");
        sb.append("id=").append(id);
        sb.append(", number='").append(number).append('\'');
        sb.append(", type=").append(type).append('\'');
        sb.append(", creationDate='").append(creationDate).append('\'');
        sb.append(", balance=").append(balance);
        sb.append(", owner=").append(owner);
        sb.append(", transactions=").append(transactions);
        sb.append(", disabled=").append(disabled);
        sb.append('}');
        return sb.toString();
    }
}

