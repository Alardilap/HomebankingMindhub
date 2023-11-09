package com.mindhub.AppHomeBanking.controlers;

import com.mindhub.AppHomeBanking.dtos.ClientDTO;
import com.mindhub.AppHomeBanking.models.Client;
import com.mindhub.AppHomeBanking.repositories.ClientRepositories;
import com.mindhub.AppHomeBanking.service.ClientService;
import org.springframework.security.core.Authentication;;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class ClientController {
//    @Autowired
//    private ClientRepositories clientRepositories;

    @Autowired
    private ClientService clientService;
    @GetMapping("/clients")
    public Set<ClientDTO> getClients() {

//        List<Client> clients = clientRepositories.findAll();
//
//        Stream<Client> streamClients = clients.stream();
//
//        Stream<ClientDTO> streamClientsDto = streamClients.map(client -> new ClientDTO(client));
//
//        Set<ClientDTO> clientsDtos = streamClientsDto.collect(Collectors.toSet());
//
//        return clientsDtos;
        return clientService.getAllClientsDTO();
    }

    @GetMapping("/clients/{id}")
    public ClientDTO getClient(@PathVariable Long id) {
        return clientService.findClientById(id);
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    @RequestMapping(path = "/clients", method = RequestMethod.POST)
    public ResponseEntity<?> register(

            @RequestParam String name, @RequestParam String lastName,

            @RequestParam String email, @RequestParam String password) {

        if (name.isBlank() || lastName.isBlank() || email.isBlank() || password.isEmpty()) {
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }

        if (clientService.existsClientByEmail(email)) {
            return new ResponseEntity<>("Email in use", HttpStatus.FORBIDDEN);
        }

//        clientRepositories.save(new Client(name, lastName, email, passwordEncoder.encode(password)));
        Client client = new Client(name,lastName,email, passwordEncoder.encode(password));
        clientService.saveClient(client);

        return new ResponseEntity<>("User Created", HttpStatus.CREATED);

    }

    @RequestMapping("/current")
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





