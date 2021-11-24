package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Client;

import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toSet;

public class ClientDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Set<AccountDTO> accounts;
    private Set<ClientLoanDTO> loans;
    private Set<CardDTO> cards;
    private Set<FrequentAccountDTO> frequents;

    public ClientDTO(){

    }

    public ClientDTO(Client client){
        this.id = client.getId();
        this.firstName = client.getFirstName();
        this.lastName = client.getLastName();
        this.email = client.getEmail();
        this.accounts = client.getAccounts().stream().map(account -> new AccountDTO(account)).collect(toSet());
        this.loans = client.getLoans().stream().map(ClientLoanDTO::new).collect(toSet());
        this.cards = client.getCards().stream().map(CardDTO::new).collect(toSet());
        this.frequents = client.getFrequentAccounts().stream().map(FrequentAccountDTO::new).collect(toSet());
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<AccountDTO> getAccounts() {
        return accounts;
    }

    public void setAccounts(Set<AccountDTO> accounts) {
        this.accounts = accounts;
    }

    public Set<ClientLoanDTO> getLoans(){
        return this.loans;
    }

    public void setLoans(Set<ClientLoanDTO> loans){
        this.loans = loans;
    }

    public Set<CardDTO> getCards(){
        return this.cards;
    }

    public void setCards(Set<CardDTO> cards){
        this.cards = cards;
    }

    public Set<FrequentAccountDTO> getFrequents() {
        return frequents;
    }

    public void setFrequents(Set<FrequentAccountDTO> frequents) {
        this.frequents = frequents;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ClientDTO{");
        sb.append("id=").append(id);
        sb.append(", firstName='").append(firstName).append('\'');
        sb.append(", lastName='").append(lastName).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", accounts=").append(accounts);
        sb.append(", loans=").append(loans);
        sb.append(", cards=").append(cards);
        sb.append(", frequents=").append(frequents);
        sb.append('}');
        return sb.toString();
    }
}
