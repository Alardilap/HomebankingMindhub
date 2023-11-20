package com.mindhub.AppHomeBanking.repositories;

import com.mindhub.AppHomeBanking.models.Card;
import com.mindhub.AppHomeBanking.models.Client;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepositories extends JpaRepository<Card ,Long> {
boolean existsByNumber(String number);
boolean existsByCvv(String number);

Card findByIdAndClient(Long id, Client client);

}
