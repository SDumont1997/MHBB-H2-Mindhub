package com.mindhub.homebanking.services;

import com.mindhub.homebanking.models.Card;

import java.util.List;

public interface CardService {
    public List<Card> getAll();
    public Card getById(Long id);
    public Card getByNumber(String number);
    public Card save(Card card);
    public void delete(Card card);
}
