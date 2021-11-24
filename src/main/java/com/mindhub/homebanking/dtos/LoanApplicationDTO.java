package com.mindhub.homebanking.dtos;

public class LoanApplicationDTO {
    private Long loanId;
    private double loanAmount;
    private Integer payments;
    private String destinationAccountNumber;

    public LoanApplicationDTO(){

    }

    public LoanApplicationDTO(Long loanId, double loanAmount, Integer payments, String destinationAccountNumber){
        this.loanId = loanId;
        this.loanAmount = loanAmount;
        this.payments = payments;
        this.destinationAccountNumber = destinationAccountNumber;
    }

    public Long getLoanId() {
        return loanId;
    }

    public void setLoanId(Long loanId) {
        this.loanId = loanId;
    }

    public double getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(double loanAmount) {
        this.loanAmount = loanAmount;
    }

    public Integer getPayments() {
        return payments;
    }

    public void setPayments(Integer payments) {
        this.payments = payments;
    }

    public String getDestinationAccountNumber() {
        return destinationAccountNumber;
    }

    public void setDestinationAccountNumber(String destinationAccountNumber) {
        this.destinationAccountNumber = destinationAccountNumber;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("LoanApplicationDTO{");
        sb.append("loanId=").append(loanId);
        sb.append(", loanAmount=").append(loanAmount);
        sb.append(", payments=").append(payments);
        sb.append(", destinationAccountNumber='").append(destinationAccountNumber).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
