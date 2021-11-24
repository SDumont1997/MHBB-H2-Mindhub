package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.FrequentAccount;

public class FrequentAccountDTO {
    private Long id;
    private String number;
    private String ownerName;

    public FrequentAccountDTO(){

    }

    public FrequentAccountDTO(FrequentAccount frequentAccount){
        this.id = frequentAccount.getId();
        this.number = frequentAccount.getAccountNumber();
        this.ownerName = frequentAccount.getAccountOwner();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("FrequentAccountDTO{");
        sb.append("id=").append(id);
        sb.append(", number='").append(number).append('\'');
        sb.append(", ownerName='").append(ownerName).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
