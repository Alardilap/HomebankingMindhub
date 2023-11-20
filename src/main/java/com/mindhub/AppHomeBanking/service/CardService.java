package com.mindhub.AppHomeBanking.service;

import com.mindhub.AppHomeBanking.controlers.CardsController;
import com.mindhub.AppHomeBanking.models.Card;
import com.mindhub.AppHomeBanking.models.Client;

public interface CardService {

    void saveCard(Card card);

    boolean existsByNumber(String number);

    Card findCardById(Long id);

    Card findByIdAndClientCards(Long id, Client client);
}
