package com.mindhub.AppHomeBanking.controlers;

import com.mindhub.AppHomeBanking.dtos.ClientDTO;
import com.mindhub.AppHomeBanking.dtos.LoanApplicationDTO;
import com.mindhub.AppHomeBanking.dtos.LoanDTO;
import com.mindhub.AppHomeBanking.models.*;
import com.mindhub.AppHomeBanking.repositories.*;
import com.mindhub.AppHomeBanking.service.AccountService;
import com.mindhub.AppHomeBanking.service.ClientService;
import com.mindhub.AppHomeBanking.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class LoanController {

//    @Autowired
//    private LoanRepositories loanRepositories;

    //    @Autowired
//    private ClientRepositories clientRepositories;

//    @Autowired
//    private AccountRepositories accountRepositories;

    @Autowired
    private LoanService loanService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private TransactionRepositories transactionRepositories;

    @Autowired
    private ClientLoanRepositories clientLoanRepositories;

    @Transactional
    @RequestMapping(value="/loans", method = RequestMethod.POST)
    public ResponseEntity<String> addLoan(@RequestBody LoanApplicationDTO loanDTO, Authentication authentication) {

        Client client = clientService.findClientAuthentiByEmail(authentication.getName());
//        Loan loan = loanRepositories.findById(loanDTO.getId()).orElse(null);
        Loan loan= loanService.findLoanById(loanDTO.getId());

//        if(!(loanDTO.getPayments() >= 6 && loanDTO.getPayments() <=60 && loanDTO.getPayments() % 2 == 0)){
//            return  new ResponseEntity<>("Ingrese numero de cuotas valido", HttpStatus.FORBIDDEN);
//        }
        if (loan == null) {
            return new ResponseEntity<>("Loan not found", HttpStatus.FORBIDDEN);
        }

        if (loanDTO.getAmount() <= 0 || loanDTO.getAmount().isNaN()) {
            return new ResponseEntity<>("Enter a valid amount", HttpStatus.FORBIDDEN);
        }

        if (loanDTO.numberAccount.isBlank() || client.getAccounts().stream().noneMatch(account -> account.getNumber().equals(loanDTO.getNumberAccount()))) {
            return new ResponseEntity<>("Elija una cuenta valida para realizar el desembolso", HttpStatus.FORBIDDEN);
        }
        if (loanDTO.getAmount() > loan.getMaxAmount()) {
            return new ResponseEntity<>("Monto solicitado no permitido", HttpStatus.FORBIDDEN);
        }
        if (!loan.getPayments().contains(loanDTO.getPayments())) {
            return new ResponseEntity<>("Numero de cuotas no disponible", HttpStatus.FORBIDDEN);
        }


            double amount = loanDTO.getAmount();
            double increase = amount * 0.20;
            double newAmount = amount + increase;

//            Account accountOrigen = accountRepositories.findByNumber(loanDTO.getNumberAccount());
            Account accountOrigen =accountService.findAccountNumber(loanDTO.getNumberAccount());

            Transaction credit = new Transaction(TransactionType.CREDIT, "loan " + loan.getName() + " loan approved", LocalDateTime.now(), loanDTO.getAmount());
            ClientLoan prestamo = new ClientLoan(newAmount, loanDTO.getPayments());

            accountOrigen.setBalance(accountOrigen.getBalance() + loanDTO.getAmount());
            accountOrigen.addTransaction(credit);
            client.addClientLoan(prestamo);
            loan.addClientLoan(prestamo);

            accountService.accountSave(accountOrigen);
            transactionRepositories.save(credit);
            clientLoanRepositories.save(prestamo);
            loanService.saveLoan(loan);
           clientService.saveClient(client);

            return  new ResponseEntity<>("Loan Approved", HttpStatus.CREATED);

}
    @GetMapping("/loans")
    public Set<LoanDTO> getLoans() {
        return loanService.findAllLoans();
    }

}
