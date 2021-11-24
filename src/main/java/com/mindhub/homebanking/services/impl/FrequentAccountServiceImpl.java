package com.mindhub.homebanking.services.impl;

import com.mindhub.homebanking.models.FrequentAccount;
import com.mindhub.homebanking.repositories.FrequentAccountRepository;
import com.mindhub.homebanking.services.FrequentAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FrequentAccountServiceImpl implements FrequentAccountService {
    @Autowired
    private FrequentAccountRepository frequentAccountRepository;

    @Override
    public List<FrequentAccount> getAll() {
        return frequentAccountRepository.findAll();
    }

    @Override
    public FrequentAccount getById(Long id) {
        return frequentAccountRepository.findById(id).get();
    }

    @Override
    public FrequentAccount save(FrequentAccount frequentAccount) {
        return frequentAccountRepository.save(frequentAccount);
    }
}
