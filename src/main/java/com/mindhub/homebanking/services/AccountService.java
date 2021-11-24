package com.mindhub.homebanking.services;

import com.mindhub.homebanking.models.Account;

import java.util.List;

public interface AccountService {

    public List<Account> getAll();
    public Account getById(Long id);
    public Account getByNumber(String number);
    public Account save(Account account);
}
