package com.mindhub.AppHomeBanking.dtos;

import com.mindhub.AppHomeBanking.models.Account;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

public class AccountDTO {
    private Long id;
    private String number;
    private LocalDate creationDate;
    private double balance;

    private Set<TransactionDTO> transactions;

    public AccountDTO(Account account) {
        number = account.getNumber();
        id = account.getId();
        creationDate = account.getCreationDate();
        balance = account.getBalance();
        this.transactions = account.getTransactions().stream().map(transaction -> new TransactionDTO(transaction)).collect(Collectors.toSet());
    }

    public Long getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public double getBalance() {
        return balance;
    }

    public Set<TransactionDTO> getTransactions() {
        return transactions;
    }
}
