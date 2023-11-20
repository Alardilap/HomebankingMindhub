package com.mindhub.AppHomeBanking.controlers;

import com.mindhub.AppHomeBanking.models.*;
import com.mindhub.AppHomeBanking.service.AccountService;
import com.mindhub.AppHomeBanking.service.ClientService;
import com.mindhub.AppHomeBanking.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Set;


@RestController
@RequestMapping("/api")
public class TransactionController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private TransactionService transactionService;

    @Transactional
 @PostMapping(path = "/transactions")
    public ResponseEntity<?> addTransaction(@RequestParam Double amount, @RequestParam String description, @RequestParam String originnumber, @RequestParam String destinationnumber, Authentication authentication) {

        if (description.isBlank()) {
            return new ResponseEntity<>("The description cannot be empty", HttpStatus.FORBIDDEN);
        }

        if (originnumber.isEmpty() || destinationnumber.isEmpty()) {
            return new ResponseEntity<>("Please indicate the account number", HttpStatus.FORBIDDEN);
        }
        if (originnumber.equals(destinationnumber)) {
            return new ResponseEntity<>("Please enter a valid account number", HttpStatus.FORBIDDEN);
        }

        Account accountOrigen = accountService.findAccountNumber(originnumber);
        Account accountDestino = accountService.findAccountNumber(destinationnumber);

        if (accountDestino == null) {
            return new ResponseEntity<>("Account not found, please enter a valid account", HttpStatus.FORBIDDEN);
        }
        if (accountOrigen == null) {
            return new ResponseEntity<>("Account not found, please enter a valid account", HttpStatus.FORBIDDEN);
        }

        String nameClient = authentication.getName();
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

        Transaction debit = new Transaction(TransactionType.DEBIT, "to " + destinationnumber + ": " + description, LocalDateTime.now(), Double.parseDouble("-" + amount),accountOrigen.getBalance()-amount,true);
        Transaction credit = new Transaction(TransactionType.CREDIT, "from " + originnumber + ": " + description, LocalDateTime.now(), Double.parseDouble("+" + amount),accountDestino.getBalance()+amount,true);
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