package com.mindhub.homebanking.services.impl;

import com.mindhub.homebanking.models.Loan;
import com.mindhub.homebanking.repositories.LoanRepository;
import com.mindhub.homebanking.services.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoanServiceImpl implements LoanService {
    @Autowired
    private LoanRepository loanRepository;


    @Override
    public List<Loan> getAll() {
        return loanRepository.findAll();
    }

    @Override
    public Loan getById(Long id) {
        return loanRepository.findById(id).get();
    }

    @Override
    public Loan save(Loan loan) {
        return loanRepository.save(loan);
    }
}
