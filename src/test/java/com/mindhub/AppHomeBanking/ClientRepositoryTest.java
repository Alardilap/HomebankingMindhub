package com.mindhub.AppHomeBanking;

import com.mindhub.AppHomeBanking.models.Client;
import com.mindhub.AppHomeBanking.repositories.ClientRepositories;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ClientRepositoryTest {

    @Autowired
    private ClientRepositories clientRepositories;

    List<Client> clients;

    @BeforeEach
    public void setUpClients(){
        clients = clientRepositories.findAll();
    }
    @Test
    public void existClients() {
        assertThat(clients, is(not(empty())));
    }
    @Test
    public void maxCharacterName(){
        int maxNameLength = 20;

        boolean verificacion = clients.stream()
                .allMatch(client -> client.getName().length() < maxNameLength);

        assertTrue(verificacion, "maximum number of characters exceeded");
//        para verificar si una condición es verdadera o falsa. La sintaxis típica de assertTrue es la siguiente:
//        assertTrue(condición, mensajeDeError);
    }
    @Test
    public void maxCharacterEmail(){
        int maxEmailLength = 35;

        Optional<Client> exceededCustomer = clients.stream()
                .filter(client -> client.getEmail().length() >= maxEmailLength)
                .findFirst();
//        Si no se encuentra ningún cliente que incumpla la condición, el Optional estará vacío (Optional.empty()),
//        si no, devuelve el cliente que incumple
        if(exceededCustomer.isPresent()){
//        isPresent para verificar si hay un client en exceededCustomer
            Client client = exceededCustomer.get();
            fail("The client " + client.getName() + " exceeded the number of characters allowed for the name with " + client.getEmail());
        }else{
            System.out.println("No client fails to comply with the maximum number of characters for the name field.");
        }
    }

    @Test
    public void maxCharacterEmailAll() {
        int maxEmailLength = 35;

        List<Client> exceededClients = clients.stream()
                .filter(client -> client.getEmail().length() >= maxEmailLength)
                .collect(Collectors.toList());

        if (!exceededClients.isEmpty()) {
            // Algunos clientes incumplen la condición
            for (Client client : exceededClients) {
//            recorre la lista excededcustomers y va asignando a la variable client del bucle
                System.out.println("The client " + client.getName() + " exceeded the number of characters allowed for the email with " + client.getEmail() + " with id " + client.getId());
            }
            // Lanza una excepción personalizada para marcar el fallo del test
            throw new AssertionError("Some clients exceeded the maximum email length.");
        } else {
            System.out.println("No clients fail to comply with the maximum number of characters for the email field.");
        }
    }

    @Test
    public void maxCharacterNameAll() {
        int maxNameLength = 20;

        List<Client> exceededClients = clients.stream()
                .filter(client -> client.getName().length() >= maxNameLength)
                .collect(Collectors.toList());

        if (!exceededClients.isEmpty()) {
            for (Client client : exceededClients) {
//            recorre la lista excededcustomers y asignando a la variable cada client de la lista
                System.out.println("The client " + client.getName() + " exceeded the number of characters allowed for the name with " + client.getEmail() + " with id " + client.getId());
            }
            throw new AssertionError("Some clients exceeded the maximum name length.");
        } else {
            System.out.println("No clients fail to comply with the maximum number of characters for the name field.");
        }
    }
}
