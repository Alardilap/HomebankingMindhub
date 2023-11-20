package com.mindhub.AppHomeBanking.controlers;

import com.mindhub.AppHomeBanking.dtos.LoanApplicationDTO;
import com.mindhub.AppHomeBanking.dtos.LoanDTO;
import com.mindhub.AppHomeBanking.models.*;
import com.mindhub.AppHomeBanking.repositories.*;
import com.mindhub.AppHomeBanking.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;


@RestController
@RequestMapping("/api")
public class LoanController {

    @Autowired
    private LoanService loanService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private ClientLoanService clientLoanService;

    @Transactional
    @PostMapping(value="/loans")
    public ResponseEntity<String> addLoan(@RequestBody LoanApplicationDTO loanDTO, Authentication authentication) {

        Client client = clientService.findClientByEmail(authentication.getName());
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
            double increase = amount * loan.getInteres() / 100;
            double newAmount = amount + increase;

            Account accountOrigen =accountService.findAccountNumber(loanDTO.getNumberAccount());

            Transaction credit = new Transaction(TransactionType.CREDIT, "Loan " + loan.getName() + " loan approved", LocalDateTime.now(), loanDTO.getAmount(),accountOrigen.getBalance()+ loanDTO.getAmount(),true);
            ClientLoan prestamo = new ClientLoan(newAmount, loanDTO.getPayments(),newAmount, loanDTO.getPayments());

            accountOrigen.setBalance(accountOrigen.getBalance() + loanDTO.getAmount());
            accountOrigen.addTransaction(credit);
            client.addClientLoan(prestamo);
            loan.addClientLoan(prestamo);

            accountService.accountSave(accountOrigen);
            transactionService.saveTransaction(credit);
            clientLoanService.saveClientLoan(prestamo);
            loanService.saveLoan(loan);
            clientService.saveClient(client);

            return  new ResponseEntity<>("Loan Approved", HttpStatus.CREATED);

}
    @GetMapping("/loans")
    public Set<LoanDTO> getLoans() {
        return loanService.findAllLoans();
    }


    @PostMapping("/newloan")
    public Object newLoan(@RequestParam String name, @RequestParam Double maxAmount,@RequestParam List<Integer> payments, @RequestParam Integer interes,Authentication authentication) {

    Client client = clientService.findClientByEmail(authentication.getName());

    if(!client.getEmail().equals("AdminAgileBank@gmail.com")){
        return  new ResponseEntity<>("Invalid user", HttpStatus.FORBIDDEN);
    }

    if(name.isBlank()){
        return  new ResponseEntity<>("Name invalid", HttpStatus.FORBIDDEN);
    }

    if (Double.compare(maxAmount, 0.0) == 0 || maxAmount<=0 || Double.isNaN(maxAmount)) {
            return new ResponseEntity<>("Invalid maximum amount", HttpStatus.FORBIDDEN);
    }
        boolean invalidPayments = false;

        if (payments.isEmpty()) {
            invalidPayments = true;
        } else {
            for (Integer payment : payments) {
                if (payment <= 0) {
                    invalidPayments = true;
                    break;
                }
            }
        }
        if (invalidPayments) {
            return new ResponseEntity<>("Enter valid number of payments", HttpStatus.FORBIDDEN);
        }

    if(interes==null || interes<=0){
        return  new ResponseEntity<>("Interes invalid",HttpStatus.FORBIDDEN);
    }

    Loan newLoan= new Loan (name,maxAmount,payments,interes);
      loanService.saveLoan(newLoan);
      return new LoanDTO(newLoan);
    }
}


