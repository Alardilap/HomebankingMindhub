package com.mindhub.AppHomeBanking.controlers;


import com.mindhub.AppHomeBanking.dtos.AccountDTO;
import com.mindhub.AppHomeBanking.repositories.AccountRepositories;
import org.aspectj.weaver.patterns.HasThisTypePatternTriedToSneakInSomeGenericOrParameterizedTypePatternMatchingStuffAnywhereVisitor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Set;
import java.util.stream.Collectors;

@RestController //Indico que esta clase sera un controlador
@RequestMapping("/api")//Defino la ruta base para este controlador
public class AccountController {

@Autowired //Para crear una instancia de una clase, poder usar mi repositorio
    private AccountRepositories accountRepositories;

    @GetMapping("/accounts") //indico que tipo de peticion manejara este servlet y la ruta para el mismo
    public Set<AccountDTO> getAccounts (){
    return accountRepositories.findAll().stream().map(account -> new AccountDTO(account)).collect(Collectors.toSet());
    }

    @GetMapping("/account/{id}")
    public AccountDTO getAccount (@PathVariable Long id){//Esta anotación se usa para extraer valores de variables de ruta
    return accountRepositories.findById(id).map(account -> new AccountDTO(account)).orElse(null);
    }
//HTTP













}
