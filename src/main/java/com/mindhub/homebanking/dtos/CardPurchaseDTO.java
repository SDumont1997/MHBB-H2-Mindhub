package com.mindhub.homebanking.dtos;

public class CardPurchaseDTO {
    private String cardNumber;
    private Integer cvv;
    private Double amount;
    private String storeName;
    private String detail;

    public CardPurchaseDTO(){

    }

    public CardPurchaseDTO(String cardNumber, Integer cvv, Double amount, String storeName, String detail){
        this.cardNumber = cardNumber;
        this.cvv = cvv;
        this.amount = amount;
        this.storeName = storeName;
        this.detail = detail;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Integer getCvv() {
        return cvv;
    }

    public void setCvv(Integer cvv) {
        this.cvv = cvv;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CardPurchaseDTO{");
        sb.append("cardNumber='").append(cardNumber).append('\'');
        sb.append(", cvv=").append(cvv);
        sb.append(", amount=").append(amount);
        sb.append(", storeName=").append(storeName);
        sb.append(", detail='").append(detail).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
