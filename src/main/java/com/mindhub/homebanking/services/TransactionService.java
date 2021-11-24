package com.mindhub.homebanking.services;

import com.mindhub.homebanking.models.Transaction;

import java.util.List;

public interface TransactionService {
    public List<Transaction> getAll();
    public Transaction getById(Long id);
    public Transaction save(Transaction transaction);
}
