package com.mindhub.AppHomeBanking.controlers;

import com.mindhub.AppHomeBanking.dtos.ClientDTO;
import com.mindhub.AppHomeBanking.models.Client;
import com.mindhub.AppHomeBanking.service.ClientService;
import org.springframework.security.core.Authentication;;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.Set;


@RestController
@RequestMapping("/api")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @GetMapping("/clients")
    public Set<ClientDTO> getClients() {

        return clientService.getAllClientsDTO();
    }

    @GetMapping("/clients/{id}")
    public ClientDTO getClient(@PathVariable Long id) {
        return clientService.findClientById(id);
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping(path = "/clients")
    public ResponseEntity<?> register(

            @RequestParam String name, @RequestParam String lastName,

            @RequestParam String email, @RequestParam String password) {

        String regexName = "^[a-zA-Z\\s']+$";
        if (name.isBlank() || !name.matches(regexName)){
            return new ResponseEntity<>("Please enter a valid name", HttpStatus.FORBIDDEN);
        }

        if(lastName.isBlank()  || !name.matches(regexName)){
            return new ResponseEntity<>("Please enter a valid lastName", HttpStatus.FORBIDDEN);

        }if(password.isEmpty()){
            return new ResponseEntity<>("Please enter a valid password", HttpStatus.FORBIDDEN);
        }
        if(email.isEmpty()){
            return new ResponseEntity<>("Please enter a valid email", HttpStatus.FORBIDDEN);
        }

        if (clientService.existsClientByEmail(email)) {
            return new ResponseEntity<>("Email in use", HttpStatus.FORBIDDEN);
        }

//        clientRepositories.save(new Client(name, lastName, email, passwordEncoder.encode(password)));
        Client client = new Client(name,lastName,email, passwordEncoder.encode(password));
        clientService.saveClient(client);

        return new ResponseEntity<>("User Created", HttpStatus.CREATED);

    }

    @GetMapping("/current")
    public ClientDTO getCurrentClient(Authentication authentication) {
        if (authentication != null) {
            String email = authentication.getName();
            Client client = clientService.findClientByEmail(email);
            if (client != null) {
                ClientDTO clientDTO = new ClientDTO(client);
                return clientDTO;
            }
        }
        return null;
    }
}
