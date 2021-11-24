package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toSet;

@Entity
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    @OneToMany(mappedBy = "owner", fetch = FetchType.EAGER)
    private Set<Account> accounts = new HashSet<>();
    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    private Set<ClientLoan> loans = new HashSet<>();
    @OneToMany(mappedBy = "cardholder", fetch = FetchType.EAGER)
    private Set<Card> cards = new HashSet<>();
    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    private Set<FrequentAccount> frequentAccounts = new HashSet<>();

    public Client() {

    }

    public Client(String first, String last, String email, String password) {
        this.firstName = first;
        this.lastName = last;
        this.email = email;
        this.password = password;
    }

    public Long getId() { return this.id; }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String first) {
        this.firstName = first;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String last) {
        this.lastName = last;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() { return this.password; }

    public void setPassword(String password) { this.password = password; }

    public Set<Account> getAccounts(){
        return this.accounts;
    }

    public void addAccount(Account account){
        account.setOwner(this);
        accounts.add(account);
    }

    public Set<ClientLoan> getLoans(){
        return this.loans;
    }

    public void addLoan(ClientLoan loan){
        loan.setClient(this);
        loans.add(loan);
    }

    public Set<Card> getCards(){
        return this.cards;
    }

    public Set<Card> getCards(CardType cardType) {
        return this.cards.stream().filter(card -> card.getCardType().equals(cardType)).collect(toSet());
    }

    public void addCard(Card card){
        card.setCardholder(this);
        cards.add(card);
    }

    public Set<FrequentAccount> getFrequentAccounts(){
        return this.frequentAccounts;
    }

    public void addFrequentAccount(FrequentAccount frequentAccount) {
        this.frequentAccounts.add(frequentAccount);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Client{");
        sb.append("id=").append(id);
        sb.append(", firstName='").append(firstName).append('\'');
        sb.append(", lastName='").append(lastName).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append(", accounts=").append(accounts);
        sb.append(", loans=").append(loans);
        sb.append(", cards=").append(cards);
        sb.append(", frequentAccounts=").append(frequentAccounts);
        sb.append('}');
        return sb.toString();
    }
}
