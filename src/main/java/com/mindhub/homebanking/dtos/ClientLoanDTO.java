package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.ClientLoan;
import com.mindhub.homebanking.models.Loan;

public class ClientLoanDTO {
    private Long id;
    private Long loanId;
    private String loanType;
    private double amount;
    private Integer payments;

    public ClientLoanDTO(){

    }

    public ClientLoanDTO(ClientLoan clientLoan){
        this.id = clientLoan.getId();
        this.loanId = clientLoan.getLoan().getId();
        this.loanType = clientLoan.getLoan().getName();
        this.amount = clientLoan.getAmount();
        this.payments = clientLoan.getPayments();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLoanId(){
        return this.loanId;
    }

    public void setLoanId(Loan loan){
        this.loanId = loan.getId();
    }

    public String getLoanType(){
        return this.loanType;
    }

    public void setLoanType(Loan loan) {
        this.loanType = loan.getName();
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Integer getPayments() {
        return payments;
    }

    public void setPayments(Integer payments) {
        this.payments = payments;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ClientLoanDTO{");
        sb.append("id=").append(id);
        sb.append(", loanId=").append(loanId);
        sb.append(", loanType=").append(loanType);
        sb.append(", amount=").append(amount);
        sb.append(", payments=").append(payments);
        sb.append('}');
        return sb.toString();
    }
}
