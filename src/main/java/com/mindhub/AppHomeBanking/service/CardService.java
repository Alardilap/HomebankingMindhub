package com.mindhub.AppHomeBanking.service;

import com.mindhub.AppHomeBanking.models.Card;

public interface CardService {

    void saveCard(Card card);

    boolean existsByNumber(String number);


}
