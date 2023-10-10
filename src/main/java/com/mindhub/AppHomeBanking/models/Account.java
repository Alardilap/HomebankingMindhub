package com.mindhub.AppHomeBanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO,generator="native")
    @GenericGenerator(name="native" ,strategy="native")
    private Long id;
    private String number;
    private LocalDate creationDate;
    private double balance;



    //Establezco el tipo de relación que tendran mis clases
    @ManyToOne(fetch = FetchType.EAGER)
    //Propiedad donde voy a ver el cliente perteneciente de esta cuenta
    @JoinColumn(name= "clienteAsociado")
    private Client client;

@OneToMany(mappedBy="account", fetch = FetchType.EAGER)
private Set<Transaction> transactions = new HashSet<>();

    public Account(){

    }
     public Account (String number , LocalDate creationDate, double balance){
        this.number= number;
        this.creationDate= creationDate;
        this.balance=balance;
     }

    public String getNumber() {
        return number;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNumber(String number) {
        this.number = number;
    }


    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }


    public Set<Transaction> getTransactions() {
        return transactions;
    }

    public void addTransaction(Transaction transaction){//Este metodo recibe una cuenta(objeto) de la clase Account
        transaction.setAccount(this) ;//Le Asigno la cuenta al cliente que este llamando este metodo
        transactions.add(transaction); //A la propiedad accounts de esta clase, le vamos a agregar la cuenta que recibimos por parametro
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", creationDate=" + creationDate +
                ", balance=" + balance +
                ", client=" + client +
                '}';
    }
}
