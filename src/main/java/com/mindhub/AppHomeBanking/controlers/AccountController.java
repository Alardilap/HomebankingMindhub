package com.mindhub.AppHomeBanking.controlers;

import com.mindhub.AppHomeBanking.Utils.CardUtils;
import com.mindhub.AppHomeBanking.dtos.AccountDTO;
import com.mindhub.AppHomeBanking.models.*;
import com.mindhub.AppHomeBanking.service.AccountService;
import com.mindhub.AppHomeBanking.service.ClientService;
import com.mindhub.AppHomeBanking.service.LoanService;
import com.mindhub.AppHomeBanking.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.print.DocFlavor;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class AccountController {

    @Autowired
    private LoanService loanService;
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private AccountService accountService;

    @Autowired
    private ClientService clientService;

    @GetMapping("/accounts")
    public Set<AccountDTO> getAccounts() {
        return accountService.getAllAccountsDTO();
    }

    @GetMapping("/accounts/{id}")
    public AccountDTO getAccount(@PathVariable Long id) {//Esta anotación se usa para extraer valores de variables de ruta
        return accountService.findAccountById(id);
    }

    @PostMapping(path = "/clients/current/accounts")
    public ResponseEntity<?> addAccount(@RequestParam String accountType, Authentication authentication) {

        Client client = clientService.findClientByEmail(authentication.getName());

        if(authentication.getName().isBlank()){
            return new ResponseEntity<>("Invalid user",HttpStatus.FORBIDDEN);
        }

       if(accountType.isBlank()){
         return new ResponseEntity<>("invalid account type",HttpStatus.FORBIDDEN);
     }

        if (client != null && client.getAccounts().stream().filter(account -> account.getActive()).count() < 3 && accountType.equals(AccountType.CURRENT.toString())) {

            String accountNumber = CardUtils.getRandomNumber();

            Account account = new Account();
            account.setNumber(accountNumber);
            account.setCreationDate(LocalDate.now());
            account.setBalance(0);
            account.setActive(true);
            account.setAccountType(AccountType.CURRENT);
            client.addAccount(account);
            accountService.accountSave(account);
            clientService.saveClient(client);

            return new ResponseEntity<>("Account Created", HttpStatus.CREATED);
        }
        if (client != null && client.getAccounts().stream().filter(account -> account.getActive()).count() < 3 && accountType.equals(AccountType.SAVINGS.toString())) {

            String accountNumber = CardUtils.getRandomNumber();

            Account account = new Account();
            account.setNumber(accountNumber);
            account.setCreationDate(LocalDate.now());
            account.setBalance(0);
            account.setActive(true);
            account.setAccountType(AccountType.SAVINGS);
            client.addAccount(account);
            accountService.accountSave(account);
            clientService.saveClient(client);

            return new ResponseEntity<>("Account Created", HttpStatus.CREATED);
        }

        else {
            return new ResponseEntity<>("It is not possible to create another account", HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/clients/current/accounts")
    public Set<AccountDTO> getAccounts(Authentication authentication) {
        Client client = clientService.findClientByEmail(authentication.getName());
        Set<AccountDTO> accountDTOS = client.getAccounts().stream().map(account -> new AccountDTO(account)).filter(AccountDTO::getActive).collect(Collectors.toSet());
        if (client != null && accountDTOS != null) {
            return accountDTOS;
        } else {
            return new HashSet<>();
        }
    }
    @GetMapping("/accountType")
    public Set<AccountType> getAccountType() {
        AccountType[] accountType = AccountType.values();
        Set<AccountType> accountTypeSet = new HashSet<>(Arrays.asList(accountType));
        return accountTypeSet;
    }

    @PatchMapping("/account/modify")
    public ResponseEntity<?> accountModify(@RequestParam String number, Authentication authentication) {

        if (authentication.getName() == null) {
            return new ResponseEntity<>("Client must be authenticated",HttpStatus.FORBIDDEN);
        }

        if (number.isBlank()) {
            return new ResponseEntity<>("Missing account number",HttpStatus.FORBIDDEN);
        }
        Account account = accountService.findAccountNumber(number);
        Client client = clientService.findClientByEmail(authentication.getName());
        if (account == null) {
            return new ResponseEntity<>("Account do not exist",HttpStatus.FORBIDDEN);
        }
        if (account.getBalance() != 0) {
            return new ResponseEntity<>("Account balance must be 0",HttpStatus.FORBIDDEN);
        }
        Boolean verificationAccount = client.getAccounts().stream().noneMatch(account1 -> account1.getNumber().equals(number));
        if (verificationAccount) {
            return new ResponseEntity<>("Invalid account",HttpStatus.FORBIDDEN);
        }

        Set<Transaction> transactions = account.getTransactions().stream().collect(Collectors.toSet());
        transactions.forEach(trans -> {
            trans.setActive(false);
            transactionService.saveTransaction(trans);
        });

        account.setActive(false);
        accountService.accountSave(account);
        return new ResponseEntity<>("Account Deleted", HttpStatus.OK);
    }
}
