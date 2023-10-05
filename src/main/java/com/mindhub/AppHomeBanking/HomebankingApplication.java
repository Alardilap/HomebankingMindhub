package com.mindhub.AppHomeBanking;
//REST REPOSITORY

import com.mindhub.AppHomeBanking.models.Account;
import com.mindhub.AppHomeBanking.models.Client;
import com.mindhub.AppHomeBanking.repositories.AccountRepositories;
import com.mindhub.AppHomeBanking.repositories.ClientRepositories;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

@SpringBootApplication
public class HomebankingApplication {

    public static void main(String[] args) {
        SpringApplication.run(HomebankingApplication.class, args);
    }

    ;

    @Bean

    public CommandLineRunner initData(ClientRepositories clientRepositories, AccountRepositories accountRepositories) {
        return args -> {
            System.out.println("hola");
            clientRepositories.save(new Client("Alejandra", "Ardila", "Alardilap@gmail.com"));
            clientRepositories.save(new Client("Marquito", "Miquel", "Marquitoelmejor@gmail.com"));
            //CREACION CLIEMTE MELBA
            Client melba = new Client("Melba", "Morel", "melbamorel@gmail.com");
            clientRepositories.save(melba);

            //DEFINIR LA DATE
            LocalDate date = LocalDate.now();
            //CREACION CUENTA MELBA
            Account melbaAccountOne = new Account("VIN001", date, 5000);
            melba.addAccount(melbaAccountOne);
            accountRepositories.save(melbaAccountOne);

            Account melbaAccountTwo = new Account("VIN002", date.plusDays(1), 7500);
            melba.addAccount(melbaAccountTwo);
            accountRepositories.save(melbaAccountTwo);
            System.out.println(melbaAccountOne.toString());
            //CREACION CLIENTE MARCO
            Client marco = new Client("Marco", "Miquel", "Marquitoelmejor@gmail.com");
            clientRepositories.save(marco);
            //CREACION CUENTA MARCO

            Account M1M = new Account("12MM", date, 2344950.5494);
            Account M2M = new Account("13MM", date, 4573383.847);
            marco.addAccount(M1M); //ANADIENDO LA CUENTA AL CLIENTE
            marco.addAccount(M2M);
            accountRepositories.save(M1M);
            accountRepositories.save(M2M);



        };
    }
}