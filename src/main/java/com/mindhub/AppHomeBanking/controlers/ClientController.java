package com.mindhub.AppHomeBanking.controlers;

import com.mindhub.AppHomeBanking.dtos.ClientDTO;
import com.mindhub.AppHomeBanking.models.Client;
import com.mindhub.AppHomeBanking.repositories.ClientRepositories;
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
    @Autowired
    private ClientRepositories clientRepositories;

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
        return clientRepositories.findAll().stream().map(client -> new ClientDTO(client)).collect(Collectors.toSet());
    }

    @GetMapping("/clients/{id}")
    public ClientDTO getClient(@PathVariable Long id) {
        return clientRepositories.findById(id).map(ClientDTO::new).orElse(null);
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

        if (clientRepositories.findByEmail(email) != null) {
            return new ResponseEntity<>("Email in use", HttpStatus.FORBIDDEN);
        }

        clientRepositories.save(new Client(name, lastName, email, passwordEncoder.encode(password)));
        return new ResponseEntity<>("User Created", HttpStatus.CREATED);

    }

    @RequestMapping("/current")
    public ClientDTO getCurrentClient(Authentication authentication) {
        if (authentication != null) {
            String email = authentication.getName(); // Utiliza getName() para obtener el email.
            Client client = clientRepositories.findByEmail(email);
            if (client != null) {
                // Convierte el objeto Client a un ClientDTO si es necesario.
                // Supongo que tienes una lógica para esto.
                ClientDTO clientDTO = new ClientDTO(client);
                return clientDTO;
            }
        }
        return null;
    }
}





