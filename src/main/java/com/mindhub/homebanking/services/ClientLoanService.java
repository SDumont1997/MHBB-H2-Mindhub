package com.mindhub.homebanking.services;

import com.mindhub.homebanking.models.ClientLoan;

import java.util.List;

public interface ClientLoanService {
    public List<ClientLoan> getAll();
    public ClientLoan getById(Long id);
    public ClientLoan save(ClientLoan clientLoan);
}
