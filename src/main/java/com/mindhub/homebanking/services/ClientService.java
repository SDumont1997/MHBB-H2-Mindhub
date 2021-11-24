package com.mindhub.homebanking.services;

import com.mindhub.homebanking.models.Client;

import java.util.List;

public interface ClientService {

    public List<Client> getAll();
    public Client getById(Long id);
    public Client getByEmail(String email);
    public Client save(Client client);
}
