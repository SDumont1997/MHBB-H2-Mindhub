package com.mindhub.homebanking.services.impl;

import com.mindhub.homebanking.models.ClientLoan;
import com.mindhub.homebanking.repositories.ClientLoanRepository;
import com.mindhub.homebanking.services.ClientLoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientLoanServiceImpl implements ClientLoanService {
    @Autowired
    private ClientLoanRepository clientLoanRepository;

    @Override
    public List<ClientLoan> getAll() {
        return clientLoanRepository.findAll();
    }

    @Override
    public ClientLoan getById(Long id) {
        return clientLoanRepository.findById(id).get();
    }

    @Override
    public ClientLoan save(ClientLoan clientLoan) {
        return clientLoanRepository.save(clientLoan);
    }
}
