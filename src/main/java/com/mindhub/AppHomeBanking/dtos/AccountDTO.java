package com.mindhub.AppHomeBanking.dtos;

import com.mindhub.AppHomeBanking.models.Account;

import java.time.LocalDate;

public class AccountDTO {
    private Long id;
    private String number;
    private LocalDate creationDate;
    private double balance;


    public  AccountDTO (Account account){
        number= account.getNumber();
        id= account.getId();
        creationDate= account.getCreationDate();
        balance = account.getBalance();

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
}
