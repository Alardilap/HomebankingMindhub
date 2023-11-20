package com.mindhub.AppHomeBanking;

import com.mindhub.AppHomeBanking.models.Account;
import com.mindhub.AppHomeBanking.models.Client;
import com.mindhub.AppHomeBanking.models.Loan;
import com.mindhub.AppHomeBanking.repositories.AccountRepositories;
import com.mindhub.AppHomeBanking.repositories.ClientRepositories;
import com.mindhub.AppHomeBanking.repositories.LoanRepositories;
import com.mindhub.AppHomeBanking.repositories.TransactionRepositories;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.hamcrest.MatcherAssert.assertThat;


import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class RepositoriesTest {

    @Autowired
    private LoanRepositories loanRepositories;

    Loan loanMortgage, loanStaff, loanAutomotive;

    //    TEST DE LOAN
    @BeforeEach
    public void setUpLoan() {
        loanMortgage = loanRepositories.findByName("Mortgage");
        loanStaff = loanRepositories.findByName("Staff");
        loanAutomotive = loanRepositories.findByName("Automotive");
    }

    @Test
    public void existLoans() {
        List<Loan> loans = loanRepositories.findAll();
        assertThat(loans, is(not(empty())));
    }

    @Test
    public void existLoansA() {
        List<Loan> loans = loanRepositories.findAll();
        assertThat(loans, hasItem(hasProperty("name", is("Mortgage"))));
        assertThat(loans, hasItem(hasProperty("name", is("Staff"))));
        assertThat(loans, hasItem(hasProperty("name", is("Automotive"))));
    }

    @Test
    public void existPersonalLoan() {
        List<Loan> loans = loanRepositories.findAll();
        assertThat(loans, hasItem(hasProperty("name", is("Mortgage"))));
        assertThat(loans, hasItem(hasProperty("name", is("Staff"))));
        assertThat(loans, hasItem(hasProperty("name", is("Automotive"))));
    }

    @Test
    public void checkLoanAmountByName() {
        assertNotNull(loanMortgage, "No loan found with the name: " + loanMortgage.getName());
        assertNotNull(loanStaff, "No loan found with the name: " + loanStaff.getName());
        assertNotNull(loanAutomotive, "No loan found with the name: " + loanAutomotive.getName());

        assertEquals(5000000.0, loanMortgage.getMaxAmount(), "Incorrect loan amount for: " + loanMortgage.getName());
        assertEquals(1000000, loanStaff.getMaxAmount(), "Incorrect loan amount for: " + loanStaff.getName());
        assertEquals(300000, loanAutomotive.getMaxAmount(), "Incorrect loan amount for: " + loanAutomotive.getName());
    }

    @Test
    public void checknumberofpayments() {
        List<Integer> expectedPaymentsMortgage = List.of(12, 24, 36, 48, 60);
        List<Integer> expectedPaymentsStaff = List.of(6, 12, 24);
        List<Integer> expectedPaymentsAutomotive = List.of(6, 12, 24, 36);

        assertNotNull(loanMortgage, "No loan found with the name: " + loanMortgage.getName());
        assertNotNull(loanStaff, "No loan found with the name: " + loanStaff.getName());
        assertNotNull(loanAutomotive, "No loan found with the name: " + loanAutomotive.getName());

        assertEquals(expectedPaymentsMortgage, loanMortgage.getPayments(), "Incorrect loan amount for: " + loanMortgage.getName());
        assertEquals(expectedPaymentsStaff, loanStaff.getPayments(), "Incorrect loan amount for: " + loanStaff.getName());
        assertEquals(expectedPaymentsAutomotive, loanAutomotive.getPayments(), "Incorrect loan amount for: " + loanAutomotive.getName());
    }
//TEST DE CLIENT

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

//    TEST DE ACCOUNT

    @Autowired
    private AccountRepositories accountRepositories;

    List<Account> accounts;
    @BeforeEach
    public void setUpAccounts(){
        accounts = accountRepositories.findAll();
    }

    @Test
    public void existAccounts() {
        assertThat(accounts, is(not(empty())));
    }

    @Test
    public void validAccountNumber(){
        String regexMatch = "^VIN-\\d{8}$";

        List<Account> invalidAccountNumber = accounts.stream()
                .filter(account -> !account.getNumber().matches(regexMatch))
                .collect(Collectors.toList());

        if (!invalidAccountNumber.isEmpty()) {
            for (Account account : invalidAccountNumber) {
                System.out.println("The Account con el id " + account.getId() + " has an invalid account number: " + account.getNumber());
            }
            throw new AssertionError("There are accounts with an invalid account number");
        } else {
            System.out.println("All accounts are valid with respect to their account number");
        }
    }

    @Test
    public void fkValid(){

        for (Account account : accounts) {
            Long accountId = account.getClient().getId();
            Client associatedClient = clientRepositories.findById(accountId).orElse(null);
            if(associatedClient == null){
                throw new AssertionError("The account does not belong to any registered client");
            }
        }
    }

//    TEST DE TRANSACTION

    @Autowired
    private TransactionRepositories transactionRepositories;


    @Test
    public void existTransactions() {
        assertThat(accounts, is(not(empty())));
    }
}