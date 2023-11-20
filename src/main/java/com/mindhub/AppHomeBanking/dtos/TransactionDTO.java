package com.mindhub.AppHomeBanking.dtos;

import com.mindhub.AppHomeBanking.models.Transaction;
import com.mindhub.AppHomeBanking.models.TransactionType;

import java.time.LocalDateTime;

public class TransactionDTO {

    private Long id;
    private LocalDateTime Date;
    private String description;

    private TransactionType Type;
    private Double amount;

    private Double currentBalance;

    private Boolean active;
    public TransactionDTO(Transaction transaction) {
        this.id = transaction.getId();
        this.Date = transaction.getDate();
        this.description = transaction.getDescription();
        this.Type = transaction.getType();
        this.amount = transaction.getAmount();
        this.currentBalance=transaction.getCurrentBalance();
        this.active=transaction.getActive();
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getDate() {
        return Date;
    }

    public String getDescription() {
        return description;
    }

    public Double getCurrentBalance() {
        return currentBalance;
    }

    public TransactionType getType() {
        return Type;
    }

    public Double getAmount() {
        return amount;
    }

    public Boolean getActive() {
        return active;
    }
}
