package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;

    private TransactionType type;
    private Double amount;
    private String otherPart;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "account_id")
    private Account account;

    private String detail;

    private String dateTime;

    public Transaction(){

    }

    public Transaction(Account account, TransactionType type, double amount, String otherPart){
        this.type = type;
        this.amount = amount;
        this.otherPart = otherPart;
        this.dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy - hh:mm"));
        account.addTransaction(this);
    }

    public Transaction(Account account, TransactionType type, double amount, String otherPart, String detail){
        this.type = type;
        this.amount = amount;
        this.otherPart = otherPart;
        this.dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy - hh:mm"));
        this.detail = detail;
        account.addTransaction(this);
    }

    public Long getId() {
        return id;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getOtherPart() {
        return otherPart;
    }

    public void setOtherPart(String otherPart) {
        this.otherPart = otherPart;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getDetail() { return this.detail; }

    public void setDetail(String detail) { this.detail = detail; }

    public String getDateTime() { return this.dateTime; }

    public void setDateTime(LocalDateTime dateTime) { this.dateTime = dateTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy - hh:mm")); }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Transaction{");
        sb.append("id=").append(id);
        sb.append(", type=").append(type);
        sb.append(", amount=").append(amount);
        sb.append(", otherPart='").append(otherPart).append('\'');
        sb.append(", account=").append(account);
        sb.append('}');
        return sb.toString();
    }
}
