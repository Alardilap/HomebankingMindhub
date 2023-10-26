package com.mindhub.AppHomeBanking.controlers;

import com.mindhub.AppHomeBanking.dtos.AccountDTO;
import com.mindhub.AppHomeBanking.models.Account;
import com.mindhub.AppHomeBanking.models.Client;
import com.mindhub.AppHomeBanking.repositories.AccountRepositories;
import com.mindhub.AppHomeBanking.repositories.ClientRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

@RestController //Indico que esta clase sera un controlador
@RequestMapping("/api")//Defino la ruta base para este controlador
public class AccountController {

    @Autowired //Para crear una instancia de una clase, poder usar mi repositorio
    private AccountRepositories accountRepositories;

    @Autowired
    private ClientRepositories clientRepositories;


//    public AccountController(ClientRepositories clientRepositories){
//        this.clientRepositories= clientRepositories;
//    }
    @GetMapping("/accounts") //indico que tipo de peticion manejara este servlet y la ruta para el mismo
    public Set<AccountDTO> getAccounts (){
    return accountRepositories.findAll().stream().map(account -> new AccountDTO(account)).collect(Collectors.toSet());
    }

    @GetMapping("/accounts/{id}")
    public AccountDTO getAccount (@PathVariable Long id){//Esta anotación se usa para extraer valores de variables de ruta
    return accountRepositories.findById(id).map(account -> new AccountDTO(account)).orElse(null);
    }
    public String getRandomNumber() {
        int randomNumber = (int) ((Math.random() * 90000000) + 10000000);
        return "VIN-" + randomNumber;
    }

    @RequestMapping(path = "/clients/current/accounts", method = RequestMethod.POST)
    public ResponseEntity<?> addAccount(Authentication authentication) {

        Client client = clientRepositories.findByEmail(authentication.getName());
        System.out.println(client);
        if (client != null && client.getAccounts().size() < 3 ) {

                String accountNumber = getRandomNumber();

                Account account = new Account();
                account.setNumber(accountNumber);
                account.setCreationDate(LocalDate.now());
                account.setBalance(0);

                client.addAccount(account);
                accountRepositories.save(account);
                clientRepositories.save(client);

                return new ResponseEntity<>("Account Created", HttpStatus.CREATED);
             } else {
            return new ResponseEntity<>("It is not possible to create another account", HttpStatus.FORBIDDEN);
        }

}
}
