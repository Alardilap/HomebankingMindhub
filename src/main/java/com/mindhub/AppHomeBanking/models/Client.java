package com.mindhub.AppHomeBanking.models;
//Esta línea de código especifica el paquete al que pertenece la clase Client. Los paquetes son una forma de organizar y agrupar clases relacionadas en un proyecto Java

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity//crea una tabla en la bd con los datos o propiedades de esta clase Client
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private String name;
    private String lastName;
    private String email;

    //Establezco el tipo de relacion que tendran mis clases
    //Cuando vaya a la bd nos traiga la propiedad client, o sea propiedad donde se estable la relación.
    // o donde se va a dar la relación
    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)//propiedad donde tendre las cuentas pertenecientes a este cliente
    private Set<Account> accounts = new HashSet<>();// new Hashset<>() generar un espacio para mi lista, constructor de Set.

    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    private Set<ClientLoan> clientLoans = new HashSet<>();

    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    private Set<Card> cards = new HashSet<>();
    public Client() {
    };

    public Client(String name, String lastName, String email) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
    }

    //Metodos
    @JsonIgnore
    public List<Loan> getLoans(){
        return  clientLoans.stream().map(Loan -> Loan.getLoans()).collect(Collectors.toList());
    };

    public void getLoans(Client client){
     client.getLoans(this);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getId() {
        return id;
    }

    public Set<Account> getAccount() {
        return accounts;
    }

    public Set<ClientLoan> getClientLoans() {
        return clientLoans;
    }

    public Set<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(Set<Account> accounts) {
        this.accounts = accounts;
    }

    public void setClientLoans(Set<ClientLoan> clientLoans) {
        this.clientLoans = clientLoans;
    }

    public Set<Card> getCards() {
        return cards;
    }

    public void setCards(Set<Card> cards) {
        this.cards = cards;
    }

    public void addClientLoan(ClientLoan clientLoan){
        clientLoan.setClients(this);
        clientLoans.add(clientLoan);
    }

    public void addAccount(Account account) {//Este metodo recibe una cuenta(objeto) de la clase Account
        account.setClient(this);//Le Asigno la cuenta al cliente que este llamando este metodo
        accounts.add(account);//A la propiedad accounts de esta clase, le vamos a agregar la cuenta que recibimos por parametro
    }
    public void addCard(Card card) {
        card.setClient(this);
        cards.add(card);
    }

    public void addAllCards(List<Card> cards){
       for (Card card : cards){
           this.addCard(card);
       }
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", accounts=" + accounts +
                '}';
    }
}
