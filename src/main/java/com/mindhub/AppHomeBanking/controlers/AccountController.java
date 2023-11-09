package com.mindhub.AppHomeBanking.controlers;

import com.mindhub.AppHomeBanking.dtos.AccountDTO;
import com.mindhub.AppHomeBanking.models.Account;
import com.mindhub.AppHomeBanking.models.Client;
import com.mindhub.AppHomeBanking.repositories.AccountRepositories;
import com.mindhub.AppHomeBanking.repositories.ClientRepositories;
import com.mindhub.AppHomeBanking.service.AccountService;
import com.mindhub.AppHomeBanking.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@RestController //Indico que esta clase sera un controlador
@RequestMapping("/api")//Defino la ruta base para este controlador
public class AccountController {

    @Autowired //Para crear una instancia de una clase, poder usar mi repositorio
    private AccountService accountService;

//    @Autowired
//    private ClientRepositories clientRepositories;
    @Autowired
    private ClientService clientService;

//    public AccountController(ClientRepositories clientRepositories){
//        this.clientRepositories= clientRepositories;
//    }
    @GetMapping("/accounts") //indico que tipo de peticion manejara este servlet y la ruta para el mismo
    public Set<AccountDTO> getAccounts (){
    return accountService.getAllAccountsDTO();
    }

    @GetMapping("/accounts/{id}")
    public AccountDTO getAccount (@PathVariable Long id){//Esta anotación se usa para extraer valores de variables de ruta
    return accountService.findAccountById(id);
    }
    public String getRandomNumber() {
        int randomNumber = (int) ((Math.random() * 90000000) + 10000000);
        return "VIN-" + randomNumber;
    }

    @RequestMapping(path = "/clients/current/accounts", method = RequestMethod.POST)
    public ResponseEntity<?> addAccount(Authentication authentication) {

//        Client client = clientRepositories.findByEmail(authentication.getName());
        Client client= clientService.findClientAuthentiByEmail(authentication.getName());
        System.out.println(client);
        if (client != null && client.getAccounts().size() < 3 ) {

                String accountNumber = getRandomNumber();

                Account account = new Account();
                account.setNumber(accountNumber);
                account.setCreationDate(LocalDate.now());
                account.setBalance(0);
                client.addAccount(account);
                accountService.accountSave(account);
                clientService.saveClient(client);

                return new ResponseEntity<>("Account Created", HttpStatus.CREATED);
             } else {
            return new ResponseEntity<>("It is not possible to create another account", HttpStatus.FORBIDDEN);
        }
}
   @GetMapping("/clients/current/accounts")
        public Set<AccountDTO> getAccounts(Authentication authentication) {
//       Client client = clientRepositories.findByEmail(authentication.getName());
       Client client= clientService.findClientAuthentiByEmail(authentication.getName());
       Set<AccountDTO> accountDTOS = client.getAccounts().stream().map(account -> new AccountDTO(account)).collect(Collectors.toSet());
       if(client != null && accountDTOS != null) {
           return accountDTOS;
       }else{
           return new HashSet<>();
   }
   }
}
