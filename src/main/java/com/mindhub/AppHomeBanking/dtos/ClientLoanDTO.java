package com.mindhub.AppHomeBanking.dtos;

import com.mindhub.AppHomeBanking.models.Client;
import com.mindhub.AppHomeBanking.models.ClientLoan;
import com.mindhub.AppHomeBanking.models.Loan;

import java.util.Set;
import java.util.stream.Collectors;

public class ClientLoanDTO {
    private Long id;
    private Long idLoan;
    private String nameLoan;
    private double amount;
    private int payments;
    private Double remainAmount;
    private Integer remainPayments;

    public ClientLoanDTO(ClientLoan clientLoan) {
   this.id= clientLoan.getId();
   this.idLoan=clientLoan.getLoans().getId();
   this.nameLoan=clientLoan.getLoans().getName();
   this.amount=clientLoan.getAmount();
   this.payments=clientLoan.getPayments();
   this.remainAmount= clientLoan.getRemainAmount();
   this.remainPayments= clientLoan.getRemainPayments();
    }

    public Long getId() {
        return id;
    }

    public Long getIdLoan() {
        return idLoan;
    }

    public String getNameLoan() {
        return nameLoan;
    }

    public double getAmount() {
        return amount;
    }

    public int getPayments() {
        return payments;
    }

    public Double getRemainAmount() {
        return remainAmount;
    }

    public Integer getRemainPayments() {
        return remainPayments;
    }
}
