package com.mindhub.AppHomeBanking.dtos;

import com.mindhub.AppHomeBanking.models.Client;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ClientDTO {

    private String name;
    private String lastName;
    private String email;
private Long id;

    private List<AccountDTO> accounts;
    public ClientDTO(Client client) {

        this.id = client.getId();

        this.name= client.getName();

        this.lastName = client.getLastName();

        this.email = client.getEmail();

        this.accounts = client //objeto de tipo Client
                .getAccount() // set de account
                .stream()// stream de account
                .map(account ->new AccountDTO(account)) //stream de accounts dto
                .collect(Collectors.toList()); // set de accounts dto
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

    public Long getId() {
        return id;
    }

    public List<AccountDTO> getAccounts() {
        return accounts;
    }
}
