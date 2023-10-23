package com.mindhub.AppHomeBanking.dtos;

import com.mindhub.AppHomeBanking.models.Client;
import com.mindhub.AppHomeBanking.models.Loan;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ClientDTO {
    private Long id;
    private String name;
    private String lastName;
    private String email;
    private String password;
    private List<AccountDTO> accounts;
    private List<ClientLoanDTO> loans;
    private List<CardDTO> cards;

    public ClientDTO() {
    }
    public ClientDTO(Client client) {
        this.id = client.getId();
        this.name = client.getName();
        this.lastName = client.getLastName();
        this.email = client.getEmail();
        this.password= client.getPassword();
        this.accounts = client
                .getAccount()
                .stream()
                .map(account -> new AccountDTO(account))
                .collect(Collectors.toList());
        this.loans = client
                .getClientLoans()
                .stream()
                .map(loan -> new ClientLoanDTO(loan))
                .collect(Collectors.toList());
        this.cards = client
                .getCards()
                .stream()
                .map(card -> new CardDTO(card))
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

    public String getPassword() {
        return password;
    }
    public List<AccountDTO> getAccounts() {
        return accounts;
    }

    public List<ClientLoanDTO> getLoans() {
        return loans;
    }

    public List<CardDTO> getCards() {
        return cards;
    }

    public void setError(String missingData) {
    }
}
