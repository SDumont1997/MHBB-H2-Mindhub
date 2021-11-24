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
    private String fromDate;
    private String thruDate;

    public Card(){

    }

    public Card(Client cardholder, CardType cardType, CardColor cardColor, String number, Integer cvv, LocalDate fromDate, LocalDate thruDate){
        this.cardholder = cardholder;
        this.cardType = cardType;
        this.cardColor = cardColor;
        this.number = number;
        this.cvv = cvv;
        this.fromDate = fromDate.format(DateTimeFormatter.ofPattern("MM/yy"));
        this.thruDate = thruDate.format(DateTimeFormatter.ofPattern("MM/yy"));
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

    public String getFromDate() {
        return this.fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate.format(DateTimeFormatter.ofPattern("MM/yy"));
    }

    public String getThruDate() {
        return this.thruDate;
    }

    public void setThruDate(LocalDate thruDate) {
        this.thruDate = thruDate.format(DateTimeFormatter.ofPattern("MM/yy"));
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
        sb.append('}');
        return sb.toString();
    }
}
