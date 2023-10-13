package com.mindhub.AppHomeBanking.controlers;


import com.mindhub.AppHomeBanking.dtos.ClientDTO;
import com.mindhub.AppHomeBanking.models.Client;
import com.mindhub.AppHomeBanking.repositories.ClientRepositories;
import org.springframework.beans.factory.annotation.Autowired;
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

    ;

    @GetMapping("/clients/{id}")
    public ClientDTO getClient(@PathVariable Long id) {
        return clientRepositories.findById(id).map(ClientDTO::new).orElse(null);
    }


}

