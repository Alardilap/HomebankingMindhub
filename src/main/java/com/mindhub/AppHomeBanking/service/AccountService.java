package com.mindhub.AppHomeBanking.service;

import com.mindhub.AppHomeBanking.dtos.AccountDTO;
import com.mindhub.AppHomeBanking.models.Account;

import java.util.Set;

public interface AccountService {

    Set<AccountDTO> getAllAccountsDTO ();
    AccountDTO findAccountById( Long id);
    void accountSave(Account account);
    Account findAccountNumber (String number);
}
