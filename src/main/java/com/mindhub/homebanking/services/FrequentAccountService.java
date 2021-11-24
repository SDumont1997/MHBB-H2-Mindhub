package com.mindhub.homebanking.services;

import com.mindhub.homebanking.models.FrequentAccount;

import java.util.List;

public interface FrequentAccountService {
    public List<FrequentAccount> getAll();

    public FrequentAccount getById(Long id);

    public FrequentAccount save(FrequentAccount frequentAccount);
}
