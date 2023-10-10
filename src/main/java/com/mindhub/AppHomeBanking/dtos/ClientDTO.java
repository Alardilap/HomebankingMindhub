package com.mindhub.AppHomeBanking.dtos;

import com.mindhub.AppHomeBanking.models.Client;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ClientDTO {
    private Long id;
    private String name;
    private String lastName;
    private String email;


    private List<AccountDTO> accounts;
    public ClientDTO(Client client) {

        this.id = client.getId();

        this.name= client.getName();

        this.lastName = client.getLastName();

        this.email = client.getEmail();

        this.accounts = client
                .getAccount()
                .stream()
                .map(account ->new AccountDTO(account))
                .collect(Collectors.toList());
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public List<AccountDTO> getAccounts() {
        return accounts;
    }
}
