package com.mindhub.AppHomeBanking;
//REST REPOSITORY

import com.mindhub.AppHomeBanking.models.*;
import com.mindhub.AppHomeBanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static com.mindhub.AppHomeBanking.models.TransactionType.CREDIT;
import static com.mindhub.AppHomeBanking.models.TransactionType.DEBIT;
@SpringBootApplication
public class HomebankingApplication {

    @Autowired
    private PasswordEncoder passwordEnconder;

    public static void main(String[] args) {
        SpringApplication.run(HomebankingApplication.class, args);
    }

    @Bean
    public CommandLineRunner initData(ClientRepositories clientRepositories, AccountRepositories accountRepositories, TransactionRepositories transactionRepositories, LoanRepositories loanRepositories, ClientLoanRepositories clientLoanRepositories, CardRepositories cardRepositories) {
        return args -> {

            LocalDate date = LocalDate.now();
            LocalDateTime dateTime = LocalDateTime.now();

            Client marco = new Client("Marco", "Miquel", "Marquitoelmejor@gmail.com", passwordEnconder.encode("ale123")); //Estoy utilizando el metodo encode del objeto PasswordEncoder
            // para encriptar la contraseña de cada usuario ocliente
            clientRepositories.save(marco);

            Client admin = new Client("Admin" , "Admin", "AdminAgileBank@gmail.com", passwordEnconder.encode("administrador") );
            clientRepositories.save(admin);

            Account M1M = new Account("12MM", date, 2344950.5494);
            marco.addAccount(M1M);
            accountRepositories.save(M1M);

            Account M2M = new Account("13MM", date, 4573383.847);
            marco.addAccount(M2M);
            accountRepositories.save(M2M);

            Client melba = new Client("Melba", "Morel", "melbamorel@gmail.com",passwordEnconder.encode("marco567")); //el metodo enconde se utiliza para tomar una contraseña en texto claro
            // y generar su versión codificada.
            // Esta versión codificada es la que se almacena en la base de datos
            // y se compara con las contraseñas proporcionadas por los usuarios durante el proceso de inicio de sesión para verificar su autenticación.
            // Esto mejora significativamente la seguridad de las contraseñas en una aplicación.
            clientRepositories.save(melba);



            Account melbaAccountOne = new Account("VIN001", date, 5000.00);
            melba.addAccount(melbaAccountOne);
            accountRepositories.save(melbaAccountOne);

            Account melbaAccountTwo = new Account("VIN002", date.plusDays(1), 7500.00);
            melba.addAccount(melbaAccountTwo);
            accountRepositories.save(melbaAccountTwo);


            clientRepositories.save(new Client("Alejandra", "Ardila", "Alardilap@gmail.com", passwordEnconder.encode("marco123") ));
            clientRepositories.save(new Client("Marquito", "Miquel", "Marquitojor@gmail.com", passwordEnconder.encode("marco234")));

            Transaction firstTransaction = new Transaction(CREDIT, "Pay Cinema and Food", dateTime, 727.93);
            M2M.addTransaction(firstTransaction);
            transactionRepositories.save(firstTransaction);

            Transaction TercerTransaction = new Transaction(CREDIT, "gatos", dateTime, 123.33);
            Transaction SecondTransaction = new Transaction(DEBIT, "Pay Hotel", dateTime, -65.333);
            M1M.addTransaction(SecondTransaction);
            transactionRepositories.save(SecondTransaction);


            Transaction Primer = new Transaction(CREDIT, "Pay Taxes", dateTime, 455333.33);
            melbaAccountOne.addTransaction(Primer);
            transactionRepositories.save(Primer);

            Transaction Second = new Transaction(CREDIT, "Pay Pool", dateTime, 45.3);
            melbaAccountOne.addTransaction(Second);
            transactionRepositories.save(Second);


            Transaction Quarter = new Transaction(DEBIT, "Buy cream and beer", dateTime, -45.33);
            melbaAccountTwo.addTransaction(Quarter);
            transactionRepositories.save(Quarter);


            Transaction Fifth = new Transaction(DEBIT, "Pay Internet service", dateTime, -4.3);
            melbaAccountTwo.addTransaction(Fifth);
            transactionRepositories.save(Fifth);

            List<Integer> paymentsMortgage = List.of(12, 24, 36, 48, 60);
            Loan Mortgageloan = new Loan("mortgage", 5000000, paymentsMortgage);
            loanRepositories.save(Mortgageloan);


            List<Integer> paymentsStaff = List.of(6, 12, 24);
            Loan Staffloan = new Loan("staff", 1000000, paymentsStaff);
            loanRepositories.save(Staffloan);


            Loan Autoloan = new Loan("Automotive", 300000, List.of(6, 12, 24, 36));
            loanRepositories.save(Autoloan);


            ClientLoan clientLoanMelba1 = new ClientLoan(400000, 60);
            melba.addClientLoan(clientLoanMelba1);
            Mortgageloan.addClientLoan(clientLoanMelba1);
            clientLoanRepositories.save(clientLoanMelba1);


            ClientLoan clientLoanMelba2 = new ClientLoan(50000, 12);
            melba.addClientLoan(clientLoanMelba2);
            Staffloan.addClientLoan(clientLoanMelba2);
            clientLoanRepositories.save(clientLoanMelba2);

            ClientLoan clientLoanMarco1 = new ClientLoan(1000000, 24);
            marco.addClientLoan(clientLoanMarco1);
            Staffloan.addClientLoan(clientLoanMarco1);
            clientLoanRepositories.save(clientLoanMarco1);

            ClientLoan clientLoanMarco2 = new ClientLoan(2000000, 36);
            marco.addClientLoan(clientLoanMarco2);
            Autoloan.addClientLoan(clientLoanMarco2);
            clientLoanRepositories.save(clientLoanMarco2);

            Card cardOne =  new Card("Melba Morel",CardType.DEBIT,CardColor.GOLD,"1324-7689-9876-5673","145",date,date.plusYears(5));
            Card cardTwo =  new Card("Melba Morel",CardType.CREDIT,CardColor.TITANIUM,"1342-7219-9246-0973","123",date,date.plusYears(5));
            List<Card> cardsMelba= Arrays.asList(cardOne,cardTwo);
            melba.addAllCards(cardsMelba);
            cardRepositories.saveAll(cardsMelba);

            Card cardMarco =  new Card("Marco Miquel",CardType.CREDIT,CardColor.SILVER,"0804-1987-0804-1987","395",date,date.plusYears(5));
            marco.addCard(cardMarco);
            cardRepositories.save(cardMarco);

        };
    }
}