package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
public class ClientLoan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;
    private double amount;
    private Integer payments;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    private Client client;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "loan_id")
    private Loan loan;

    public ClientLoan(){

    }

    public ClientLoan(double amount, Integer payments, Client client, Loan loan){
        this.amount = amount;
        this.payments = payments;
        this.loan = loan;
        loan.addClientLoan(this);
        client.addLoan(this);
    }

    public Long getId(){
        return this.id;
    }

    public double getAmount(){
        return this.amount;
    }

    public void setAmount(double amount){
        this.amount = amount;
    }

    public Integer getPayments(){
        return payments;
    }

    public void setPayments(Integer payments){
        this.payments = payments;
    }

    public Client getClient(){
        return this.client;
    }

    public void setClient(Client client){
        this.client = client;
    }

    public Loan getLoan(){
        return this.loan;
    }

    public void setLoan(Loan loan){
        this.loan = loan;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ClientLoan{");
        sb.append("id=").append(id);
        sb.append(", amount=").append(amount);
        sb.append(", payments=").append(payments);
        sb.append(", client=").append(client);
        sb.append(", loan=").append(loan);
        sb.append('}');
        return sb.toString();
    }
}
