package com.mindhub.AppHomeBanking.models;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.util.Set;

@Entity
public class ClientLoan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native" , strategy = "native")
    private Long id;
    private double amount;//Monto solicitado
    private int payments;//Pagos o abono al credito
    @ManyToOne(fetch = FetchType.EAGER)
    private Client client;//clientes
    @ManyToOne(fetch = FetchType.EAGER)
    private Loan loan;//creditos
    private Double remainAmount;
    private Integer remainPayments;
    public ClientLoan(){

    }
    public ClientLoan( double amount, int payments, Double remainAmount, Integer remainPayments) {
        this.amount = amount;
        this.payments = payments;
        this.remainAmount=remainAmount;
        this.remainPayments= remainPayments;
    }

    public Long getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getPayments() {
        return payments;
    }

    public void setPayments(int payments) {
        this.payments = payments;
    }

    public Client getClients() {
        return client;
    }

    public void setClients(Client clients) {
        this.client = clients;
    }

    public Loan getLoans() {
        return loan;
    }

    public void setLoans(Loan loans) {
        this.loan = loans;
    }

    public Double getRemainAmount() {
        return remainAmount;
    }

    public void setRemainAmount(Double remainAmount) {
        this.remainAmount = remainAmount;
    }

    public Integer getRemainPayments() {
        return remainPayments;
    }

    public void setRemainPayments(Integer remainPayments) {
        this.remainPayments = remainPayments;
    }
}
