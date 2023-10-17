package com.mindhub.AppHomeBanking.repositories;

import com.mindhub.AppHomeBanking.models.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepositories extends JpaRepository<Card ,Long> {
}
