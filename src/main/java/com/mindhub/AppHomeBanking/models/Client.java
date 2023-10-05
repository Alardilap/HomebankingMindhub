package com.mindhub.AppHomeBanking.models;


//Esta línea de código especifica el paquete al que pertenece la clase Client. Los paquetes son una forma de organizar y agrupar clases relacionadas en un proyecto Java

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity//crea una tabal en la bd con los datos o propiedades de esta clase Client



public class Client {
    @Id
    @GeneratedValue(strategy =  GenerationType.AUTO, generator = "native")
    @GenericGenerator(name ="native", strategy = "native")
private long id;
    private String name;
    private String lastName;
    private String email;

    //Establezco el tipo de relacion que tendran mis clases
    //Cuando vaya a la bd nos traiga la propiedad client, o sea propiedad donde se estable la relación.
    // o donde se va a dar la relación
    @OneToMany(mappedBy = "client" , fetch = FetchType.EAGER)

    //propiedad donde tendre las cuentas pertenecientes a este cliente
private Set<Account> accounts = new HashSet<>() ;// new Hashset<>() generar un espacio para mi lista, constructor de Set.


    public Client() {
    };

    public Client(String name, String lastName, String email) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
    }

    //Metodos

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

    public void addAccount(Account account){//Este metodo recibe una cuenta(objeto) de la clase Account
        account.setClient(this) ;//Le Asigno la cuenta al cliente que este llamando este metodo
        account.add(accounts); //A la propiedad accounts de esta clase, le vamos a agregar la cuenta que recibimos por parametro
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
