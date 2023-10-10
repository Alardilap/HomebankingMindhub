package com.mindhub.AppHomeBanking;
//REST REPOSITORY

import com.mindhub.AppHomeBanking.models.Account;
import com.mindhub.AppHomeBanking.models.Client;
import com.mindhub.AppHomeBanking.models.Transaction;
import com.mindhub.AppHomeBanking.repositories.AccountRepositories;
import com.mindhub.AppHomeBanking.repositories.ClientRepositories;
import com.mindhub.AppHomeBanking.repositories.TransactionRepositories;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.mindhub.AppHomeBanking.models.TransactionType.CREDIT;
import static com.mindhub.AppHomeBanking.models.TransactionType.DEBIT;

@SpringBootApplication
public class HomebankingApplication {

    public static void main(String[] args) {
        SpringApplication.run(HomebankingApplication.class, args);
    };

    @Bean
    public CommandLineRunner initData(ClientRepositories clientRepositories, AccountRepositories accountRepositories, TransactionRepositories transactionRepositories) {
        return args -> {

            LocalDate date = LocalDate.now();
            LocalDateTime dateTime = LocalDateTime.now();

            Client marco = new Client("Marco", "Miquel", "Marquitoelmejor@gmail.com");
            clientRepositories.save(marco);

            Account M1M = new Account("12MM", date, 2344950.5494);
            marco.addAccount(M1M);
            accountRepositories.save(M1M);

            Account M2M = new Account("13MM", date, 4573383.847);
            marco.addAccount(M2M);
            accountRepositories.save(M2M);

            Client melba = new Client("Melba", "Morel", "melbamorel@gmail.com");
            clientRepositories.save(melba);


            Account melbaAccountOne = new Account("VIN001", date, 5000.00);
            melba.addAccount(melbaAccountOne);
            accountRepositories.save(melbaAccountOne);

            Account melbaAccountTwo = new Account("VIN002", date.plusDays(1), 7500.00);
            melba.addAccount(melbaAccountTwo);
            accountRepositories.save(melbaAccountTwo);


            clientRepositories.save(new Client("Alejandra", "Ardila", "Alardilap@gmail.com"));
            clientRepositories.save(new Client("Marquito", "Miquel", "Marquitoelmejor@gmail.com"));

            Transaction firstTransaction = new Transaction(CREDIT, "Pay Cinema and Food", dateTime, 727.93);
            M2M.addTransaction(firstTransaction);
            transactionRepositories.save(firstTransaction);

            Transaction TercerTransaction = new Transaction(CREDIT, "gatos", dateTime, 123.33);
            Transaction SecondTransaction = new Transaction(DEBIT, "Pay Hotel", dateTime, -65.333);
            M1M.addTransaction(SecondTransaction);
            transactionRepositories.save(SecondTransaction);


            Transaction Primer = new Transaction(CREDIT, "Pay Taxes" , dateTime, 455333.33);
            melbaAccountOne.addTransaction(Primer);
           transactionRepositories.save(Primer);

            Transaction Second = new Transaction(CREDIT, "Pay Pool" , dateTime, 45.3);
            melbaAccountOne.addTransaction(Second);
            transactionRepositories.save(Second);


            Transaction Quarter = new Transaction(DEBIT, "Buy cream and beer" , dateTime, -45.33);
            melbaAccountTwo.addTransaction(Quarter);
            transactionRepositories.save(Quarter);


            Transaction Fifth = new Transaction(DEBIT, "Pay Internet service" , dateTime, -4.3);
            melbaAccountTwo.addTransaction(Fifth);
            transactionRepositories.save(Fifth);
        };
    }
}