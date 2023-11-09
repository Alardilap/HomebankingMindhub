package com.mindhub.AppHomeBanking.controlers;

import com.mindhub.AppHomeBanking.models.*;
import com.mindhub.AppHomeBanking.repositories.AccountRepositories;
import com.mindhub.AppHomeBanking.repositories.ClientRepositories;
import com.mindhub.AppHomeBanking.repositories.TransactionRepositories;
import com.mindhub.AppHomeBanking.service.AccountService;
import com.mindhub.AppHomeBanking.service.ClientService;
import com.mindhub.AppHomeBanking.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class TransactionController {

//    @Autowired
//    private ClientRepositories clientRepositories;

    //    @Autowired
//    private TransactionRepositories transactionRepositories;

//    @Autowired
//    private AccountRepositories accountRepositories;

    @Autowired
    private AccountService accountService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private TransactionService transactionService;


    @Transactional
    @RequestMapping(path = "/transactions", method = RequestMethod.POST)
    public ResponseEntity<?> addTransaction(@RequestParam Double amount, @RequestParam String description, @RequestParam String originnumber, @RequestParam String destinationnumber, Authentication authentication) {

        if (amount == null || amount <= 0.0) {
            return new ResponseEntity<>("The value of amount is not valid", HttpStatus.FORBIDDEN);
        }

        if (description.isBlank()) {
            return new ResponseEntity<>("The description cannot be empty", HttpStatus.FORBIDDEN);
        }

        if (originnumber.isEmpty() || destinationnumber.isEmpty()) {
            return new ResponseEntity<>("Please indicate the account number", HttpStatus.FORBIDDEN);
        }
        if (originnumber.equals(destinationnumber)) {
            return new ResponseEntity<>("Please enter a valid account number", HttpStatus.FORBIDDEN);
        }

//        Account accountOrigen = accountRepositories.findByNumber(originnumber);
        Account accountOrigen = accountService.findAccountNumber(originnumber);
//        Account accountDestino = accountRepositories.findByNumber(destinationnumber);
        Account accountDestino = accountService.findAccountNumber(destinationnumber);

        if (accountDestino == null) {
            return new ResponseEntity<>("Account not found, please enter a valid account", HttpStatus.FORBIDDEN);
        }
        if (accountOrigen == null) {
            return new ResponseEntity<>("Account not found, please enter a valid account", HttpStatus.FORBIDDEN);
        }

        String nameClient = authentication.getName();
//        Client client = clientRepositories.findByEmail(nameClient);
        Client client = clientService.findClientByEmail(nameClient);

        if (client == null) {
            return new ResponseEntity<>("this request is not possible", HttpStatus.FORBIDDEN);
        }

        Set<Account> clientAccounts = client.getAccounts();
        boolean existingAccountOrigin = clientAccounts.stream().anyMatch(account -> account.getNumber().equals(originnumber));

        if (!existingAccountOrigin) {
            return new ResponseEntity<>("Please enter a valid origin account", HttpStatus.FORBIDDEN);
        }

//        if(clientRepositories.findByEmail(authentication.getName()).getAccounts().stream()
//                .noneMatch(account -> account.getNumber().equals(originnumber))){
//            return new ResponseEntity<>("this request is not possible",HttpStatus.FORBIDDEN);
//        }
        if (accountOrigen.getBalance() < amount) {
            return new ResponseEntity<>("insufficient founds", HttpStatus.FORBIDDEN);
        }

        Transaction debit = new Transaction(TransactionType.DEBIT, "to " + destinationnumber + ": " + description, LocalDateTime.now(), Double.parseDouble("-" + amount));
        Transaction credit = new Transaction(TransactionType.CREDIT, "from " + originnumber + ": " + description, LocalDateTime.now(), Double.parseDouble("+" + amount));
        accountOrigen.addTransaction(debit);
        accountDestino.addTransaction(credit);
        accountOrigen.setBalance(accountOrigen.getBalance()-amount);
        accountDestino.setBalance(accountDestino.getBalance()+amount);
        transactionService.saveTransaction(debit);
        transactionService.saveTransaction(credit);
        accountService.accountSave(accountOrigen);
        accountService.accountSave(accountDestino);

        return new ResponseEntity<>("Transaction ok", HttpStatus.OK);
    }
}
