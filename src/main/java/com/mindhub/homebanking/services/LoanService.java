package com.mindhub.homebanking.services;

import com.mindhub.homebanking.models.Loan;

import java.util.List;

public interface LoanService {
    public List<Loan> getAll();
    public Loan getById(Long id);
    public Loan save(Loan loan);
}
