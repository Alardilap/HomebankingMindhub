package com.mindhub.AppHomeBanking.repositories;

import com.mindhub.AppHomeBanking.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepositories extends JpaRepository<Account, Long> {

}
