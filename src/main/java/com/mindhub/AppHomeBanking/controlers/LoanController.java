package com.mindhub.AppHomeBanking.controlers;

import com.mindhub.AppHomeBanking.dtos.ClientDTO;
import com.mindhub.AppHomeBanking.dtos.LoanApplicationDTO;
import com.mindhub.AppHomeBanking.dtos.LoanDTO;
import com.mindhub.AppHomeBanking.models.*;
import com.mindhub.AppHomeBanking.repositories.*;
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

    @Autowired
    private LoanRepositories loanRepositories;
    @Autowired
    private ClientRepositories clientRepositories;

    @Autowired
    private TransactionRepositories transactionRepositories;

    @Autowired
    private AccountRepositories accountRepositories;
    @Autowired
    private ClientLoanRepositories clientLoanRepositories;

    @Transactional
    @RequestMapping(value="/loans", method = RequestMethod.POST)
    public ResponseEntity<String> addLoan(@RequestBody LoanApplicationDTO loanDTO, Authentication authentication) {

        Client client = clientRepositories.findByEmail(authentication.getName());
        Loan loan = loanRepositories.findById(loanDTO.getId()).orElse(null);

//        if(!(loanDTO.getPayments() >= 6 && loanDTO.getPayments() <=60 && loanDTO.getPayments() % 2 == 0)){
//            return  new ResponseEntity<>("Ingrese numero de cuotas valido", HttpStatus.FORBIDDEN);
//        }
        if (loan == null) {
            return new ResponseEntity<>("Prestamo no encontrado", HttpStatus.FORBIDDEN);
        }

        if (loanDTO.getAmount() <= 0 || loanDTO.getAmount().isNaN()) {
            return new ResponseEntity<>("Ingrese un monto valido", HttpStatus.FORBIDDEN);
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

            Account accountOrigen = accountRepositories.findByNumber(loanDTO.getNumberAccount());
            Transaction credit = new Transaction(TransactionType.CREDIT, "loan " + loan.getName() + " loan approved", LocalDateTime.now(), loanDTO.getAmount());
            ClientLoan prestamo = new ClientLoan(newAmount, loanDTO.getPayments());

            accountOrigen.setBalance(accountOrigen.getBalance() + loanDTO.getAmount());
            accountOrigen.addTransaction(credit);
            client.addClientLoan(prestamo);
            loan.addClientLoan(prestamo);

            accountRepositories.save(accountOrigen);
            transactionRepositories.save(credit);
            clientLoanRepositories.save(prestamo);
            loanRepositories.save(loan);
            clientRepositories.save(client);

            return  new ResponseEntity<>("Loan Approved", HttpStatus.CREATED);

}
    @GetMapping("/loans")
    public Set<LoanDTO> getLoans() {
        return loanRepositories.findAll().stream().map(loandb -> new LoanDTO(loandb)).collect(Collectors.toSet());
    }

}
