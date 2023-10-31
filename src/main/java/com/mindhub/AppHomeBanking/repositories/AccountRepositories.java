package com.mindhub.AppHomeBanking.repositories;

import com.mindhub.AppHomeBanking.models.Account;
import com.mindhub.AppHomeBanking.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepositories extends JpaRepository<Account, Long> {
    Account findByNumber (String number);
}
