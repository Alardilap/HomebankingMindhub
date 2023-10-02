package com.mindhub.AppHomeBanking;

import com.mindhub.AppHomeBanking.models.Client;
import com.mindhub.AppHomeBanking.repositories.ClientRepositories;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class HomebankingApplication {

    public static void main(String[] args) {
        SpringApplication.run(HomebankingApplication.class, args);
    }

    ;

    @Bean

    public CommandLineRunner initData(ClientRepositories clientRepositories) {
        return args -> {
            System.out.println("hola");
            clientRepositories.save(new Client("Alejandra","Ardila","Alardilap@gmail.com"));
            clientRepositories.save(new Client("Marquito","Miquel","Marquitoelmejor@gmail.com"));
        };
    }
}