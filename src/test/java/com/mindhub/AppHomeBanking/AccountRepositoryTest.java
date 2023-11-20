package com.mindhub.AppHomeBanking;

import com.mindhub.AppHomeBanking.models.Account;
import com.mindhub.AppHomeBanking.models.Client;
import com.mindhub.AppHomeBanking.repositories.AccountRepositories;
import com.mindhub.AppHomeBanking.repositories.ClientRepositories;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AccountRepositoryTest {

    @Autowired
    private AccountRepositories accountRepositories;

    @Autowired
    private ClientRepositories clientRepositories;

    List<Account> accounts;
    @BeforeEach
    public void setUpAccounts(){
        accounts = accountRepositories.findAll();
    }

    @Test
    public void existAccounts() {
        assertThat(accounts, is(not(empty())));
    }

    @Test
    public void validAccountNumber(){
        String regexMatch = "^VIN-\\d{8}$";

        List<Account> invalidAccountNumber = accounts.stream()
                .filter(account -> !account.getNumber().matches(regexMatch))
                .collect(Collectors.toList());

        if (!invalidAccountNumber.isEmpty()) {
            for (Account account : invalidAccountNumber) {
                System.out.println("The Account con el id " + account.getId() + " has an invalid account number: " + account.getNumber());
            }
            throw new AssertionError("There are accounts with an invalid account number");
        } else {
            System.out.println("All accounts are valid with respect to their account number");
        }
    }


    @Test
    public void fkValid(){

        for (Account account : accounts) {
            Long accountId = account.getClient().getId();
            Client associatedClient = clientRepositories.findById(accountId).orElse(null);
            if(associatedClient == null){
                throw new AssertionError("The account does not belong to any registered client");
            }
        }
    }
}
