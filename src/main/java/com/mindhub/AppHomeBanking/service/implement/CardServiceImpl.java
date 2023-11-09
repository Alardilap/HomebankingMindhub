package com.mindhub.AppHomeBanking.service.implement;

import com.mindhub.AppHomeBanking.models.Card;
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
}
