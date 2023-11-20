package com.mindhub.AppHomeBanking.models;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;
    @Enumerated(EnumType.STRING)
    public TransactionType Type;
    private Double amount;
    private LocalDateTime Date;
    private String description;
    @ManyToOne(fetch = FetchType.EAGER)
    private Account account;
    private Double currentBalance;

    private Boolean active = true;

    private Transaction() {
    }
    public Transaction(TransactionType type, String description, LocalDateTime date, Double amount, Double currentBalance, Boolean active) {
        Type = type;
        this.description = description;
        Date = date;
        this.amount = amount;
        this.currentBalance=currentBalance;
        this.active=active;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TransactionType getType() {
        return Type;
    }

    public void setType(TransactionType type) {
        Type = type;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public LocalDateTime getDate() {
        return Date;
    }

    public void setDate(LocalDateTime date) {
        Date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Double getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(Double currentBalance) {
        this.currentBalance = currentBalance;
    }
    public Boolean getActive() {
        return active;
    }
    public void setActive(Boolean active) {
        this.active = active;
    }
}
