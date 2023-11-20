package com.mindhub.AppHomeBanking.service.implement;

import com.mindhub.AppHomeBanking.dtos.AccountDTO;
import com.mindhub.AppHomeBanking.models.Account;
import com.mindhub.AppHomeBanking.repositories.AccountRepositories;
import com.mindhub.AppHomeBanking.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountRepositories accountRepositories;

    @Override
    public Set<AccountDTO> getAllAccountsDTO() {
        return accountRepositories.findAll().stream().map(account -> new AccountDTO(account)).collect(Collectors.toSet());
    }

    @Override
    public AccountDTO findAccountById(Long id) {
        return accountRepositories.findById(id).map(account -> new AccountDTO(account)).orElse(null);
    }

    @Override
    public void accountSave(Account account) {
              accountRepositories.save(account);
    }

    @Override
    public Account findAccountNumber(String number) {
        return accountRepositories.findByNumber(number);
    }
}
