package com.mindhub.AppHomeBanking.service.implement;

import com.mindhub.AppHomeBanking.models.Card;
import com.mindhub.AppHomeBanking.models.Client;
import com.mindhub.AppHomeBanking.repositories.CardRepositories;
import com.mindhub.AppHomeBanking.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CardServiceImpl implements CardService {
    @Autowired
    private CardRepositories cardRepositories;
    @Override
    public void saveCard(Card card) {
        cardRepositories.save(card);
    }

    @Override
    public boolean existsByNumber(String number) {
        return cardRepositories.existsByNumber(number);
    }

    @Override
    public Card findCardById(Long id) {
        return cardRepositories.findById(id).orElse(null);
    }

    @Override
    public Card findByIdAndClientCards(Long id, Client client) {
        return cardRepositories.findByIdAndClient(id,client);
    }

}
