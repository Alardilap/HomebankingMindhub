package com.mindhub.AppHomeBanking.repositories;

import com.mindhub.AppHomeBanking.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface TransactionRepositories extends JpaRepository<Transaction ,Long> {

}
