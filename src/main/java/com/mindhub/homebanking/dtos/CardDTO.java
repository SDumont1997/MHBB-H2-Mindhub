package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CardDTO {
    private Long id;
    private String cardholder;
    private CardType cardType;
    private CardColor cardColor;
    private String number;
    private Integer cvv;
    private String fromDate;
    private String thruDate;
    private boolean isExpired;
    private String expiredClass;

    public CardDTO(){

    }

    public CardDTO(Card card){
        this.id = card.getId();
        this.cardholder = card.getCardholder().getFirstName() + " " + card.getCardholder().getLastName();
        this.cardType = card.getCardType();
        this.cardColor = card.getCardColor();
        this.number = card.getNumber();
        this.cvv = card.getCvv();
        this.fromDate = card.getFromDate().format(DateTimeFormatter.ofPattern("MM/yy"));
        this.thruDate = card.getThruDate().format(DateTimeFormatter.ofPattern("MM/yy"));
        if (card.getThruDate().isBefore(LocalDate.now())){
            this.isExpired = true;
            this.expiredClass = "expiredCard";
        } else {
            this.isExpired = false;
            this.expiredClass = "";
        }
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCardholder() {
        return this.cardholder;
    }

    public void setCardholder(String cardholder) {
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

    public boolean isExpired() {
        return isExpired;
    }

    public void setExpired(boolean expired) {
        isExpired = expired;
    }

    public String getExpiredClass() {
        return expiredClass;
    }

    public void setExpiredClass(String expiredClass) {
        this.expiredClass = expiredClass;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CardDTO{");
        sb.append("id=").append(id);
        sb.append(", cardholder='").append(cardholder).append('\'');
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
