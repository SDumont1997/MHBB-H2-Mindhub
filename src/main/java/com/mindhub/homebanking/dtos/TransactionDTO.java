package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;

public class TransactionDTO {
    private Long id;
    private TransactionType type;
    private Double amount;
    private String otherPart;
    private String detail;
    private String date;
    private Double accountState;

    public TransactionDTO(){

    }

    public TransactionDTO(Transaction transaction){
        this.id = transaction.getId();
        this.type = transaction.getType();
        this.amount = transaction.getAmount();
        this.otherPart = transaction.getOtherPart();
        this.detail = transaction.getDetail();
        this.date = transaction.getDate();
        this.accountState = transaction.getAccountState();
    }

    public Long getId(){
        return this.id;
    }

    public TransactionType getType(){
        return this.type;
    }

    public void setType(TransactionType type){
        this.type = type;
    }

    public double getAmount(){
        return this.amount;
    }

    public void setAmount(double amount){
        this.amount = amount;
    }

    public String getOtherPart(){
        return this.otherPart;
    }

    public void setOtherPart(String otherPart){
        this.otherPart = otherPart;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String dateTime) {
        this.date = dateTime;
    }

    public Double getAccountState() {
        return accountState;
    }

    public void setAccountState(Double accountState) {
        this.accountState = accountState;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TransactionDTO{");
        sb.append("id=").append(id);
        sb.append(", type=").append(type);
        sb.append(", amount=").append(amount);
        sb.append(", otherPart='").append(otherPart).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
