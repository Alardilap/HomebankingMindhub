package com.mindhub.AppHomeBanking.models;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;
    private String name;
    private Double maxAmount;
    private Integer interes;
    @ElementCollection
    public List<Integer> payments;
//    private Double debtpayments;

    @OneToMany(mappedBy = "loan", fetch = FetchType.EAGER)
    private Set<ClientLoan> clientLoans = new HashSet<>();

    public Loan() {
    }

    public Loan(String name, double maxAmount, List<Integer> payments, Integer interes) {
        this.name = name;
        this.maxAmount = maxAmount;
        this.payments = payments;
        this.interes= interes;
    }

    public void addClientLoan(ClientLoan clientLoan){
        clientLoan.setLoans(this);
        clientLoans.add(clientLoan);
    }

    public Set<Client> getClients(){
       return this.getClientLoans().stream().map(clientLoan -> clientLoan.getClients()).collect(Collectors.toSet());
    };

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public double getMaxAmount() {
        return maxAmount;
    }
    public void setMaxAmount(double maxAmount) {
        this.maxAmount = maxAmount;
    }
    public List<Integer> getPayments() {
        return payments;
    }
    public Set<ClientLoan> getClientLoans() {
        return clientLoans;
    }
    public void setPayments(List<Integer> payments) {
        this.payments = payments;
    }

    public Integer getInteres() {
        return interes;
    }

    public void setInteres(Integer interes) {
        this.interes = interes;
    }
}
