package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Entity
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(strategy = "native", name = "native")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cardholder_id")
    private Client cardholder;

    private CardType cardType;
    private CardColor cardColor;
    private String number;
    private Integer cvv;
    private LocalDate fromDate;
    private LocalDate thruDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "account_id")
    private Account account;

    public Card(){

    }

    public Card(Client cardholder, CardType cardType, CardColor cardColor, String number, Integer cvv, LocalDate fromDate, LocalDate thruDate, String accountNumber){
        this.cardholder = cardholder;
        this.cardType = cardType;
        this.cardColor = cardColor;
        this.number = number;
        this.cvv = cvv;
        this.fromDate = fromDate;
        this.thruDate = thruDate;
        this.account = cardholder.getAccounts().stream().filter(account1 -> account1.getNumber().equals(accountNumber)).findFirst().get();
        cardholder.getAccounts().stream().filter(account1 -> account1.getNumber().equals(accountNumber)).findFirst().get().addCard(this);
    }

    public Long getId() {
        return this.id;
    }

    public Client getCardholder() {
        return this.cardholder;
    }

    public void setCardholder(Client cardholder) {
        this.cardholder = cardholder;
    }

    public CardType getCardType() {
        return this.cardType;
    }

    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }

    public CardColor getCardColor() {
        return this.cardColor;
    }

    public void setCardColor(CardColor cardColor) {
        this.cardColor = cardColor;
    }

    public String getNumber() {
        return this.number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Integer getCvv() {
        return this.cvv;
    }

    public void setCvv(Integer cvv) {
        this.cvv = cvv;
    }

    public LocalDate getFromDate() {
        return this.fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getThruDate() {
        return this.thruDate;
    }

    public void setThruDate(LocalDate thruDate) {
        this.thruDate = thruDate;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(String accountNumber) {
        this.account = this.cardholder.getAccounts().stream().filter(account1 -> account1.getNumber() == accountNumber).findFirst().get();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Card{");
        sb.append("id=").append(id);
        sb.append(", cardholder=").append(cardholder);
        sb.append(", cardType=").append(cardType);
        sb.append(", cardColor=").append(cardColor);
        sb.append(", number=").append(number);
        sb.append(", cvv=").append(cvv);
        sb.append(", fromDate=").append(fromDate);
        sb.append(", thruDate=").append(thruDate);
        sb.append(", account=").append(account);
        sb.append('}');
        return sb.toString();
    }
}
