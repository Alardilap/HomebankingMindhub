package com.mindhub.AppHomeBanking.repositories;

import com.mindhub.AppHomeBanking.models.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface LoanRepositories extends JpaRepository<Loan,Long> {

}
