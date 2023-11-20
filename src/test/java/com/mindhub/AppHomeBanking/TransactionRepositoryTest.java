package com.mindhub.AppHomeBanking;


import com.mindhub.AppHomeBanking.models.CardType;
import com.mindhub.AppHomeBanking.models.Transaction;
import com.mindhub.AppHomeBanking.repositories.TransactionRepositories;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TransactionRepositoryTest {

    @Autowired
    private TransactionRepositories transactionRepositories;

    List<Transaction> transactions;

    @BeforeEach
    public void setUpTransaction() {
        transactions = transactionRepositories.findAll();
    }

    @Test
    public void existTransactions() {
        assertThat(transactions, is(not(empty())));
    }

    @Test
    public void correctCredit() {

        String typeCredit= CardType.CREDIT.toString();

        List<Transaction> invalidTransaction = transactions.stream().
                filter(tran->  tran.getAmount() <0 && tran.getType().toString().equals(typeCredit)).
                collect(Collectors.toList());

        if(!invalidTransaction.isEmpty()){
            for(Transaction transaction: invalidTransaction){
                System.out.println("The transaction with id: " + transaction.getId() + " is invalid") ;
            }  throw new AssertionError("Transaction invalid");
        }}

    @Test
    public void correctDebit() {

        String typeCredit= CardType.DEBIT.toString();

        List<Transaction> invalidTransaction = transactions.stream().
                filter(tran->  tran.getAmount() >0 && tran.getType().toString().equals(typeCredit)).
                collect(Collectors.toList());

        if(!invalidTransaction.isEmpty()){
            for(Transaction transaction: invalidTransaction){
                System.out.println("The transaction with id: " + transaction.getId() + " is invalid") ;
            }  throw new AssertionError("Transaction invalid");
        }
    }
}
