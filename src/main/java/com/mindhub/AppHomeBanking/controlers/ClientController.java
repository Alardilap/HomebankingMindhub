package com.mindhub.AppHomeBanking.controlers;


import com.mindhub.AppHomeBanking.dtos.ClientDTO;
import com.mindhub.AppHomeBanking.models.Client;
import com.mindhub.AppHomeBanking.repositories.ClientRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class ClientController {


    @Autowired
    private ClientRepositories clientRepositories;


//    public Client addClient(@RequestBody Client client) {
//        return clientRepositories.save(client);
//    }
    @RequestMapping("/clients")
    public List<ClientDTO> getClients() {

        List<Client> clients = clientRepositories.findAll();

        Stream<Client> streamClients = clients.stream();

        Stream<ClientDTO> streamClientsDto = streamClients.map(client -> new ClientDTO(client));

        List<ClientDTO> clientsDtos = streamClientsDto.collect(Collectors.toList());

        return clientsDtos;

    };

    @RequestMapping("/clients/{id}")
    public ClientDTO getClient(@PathVariable Long id){

        return clientRepositories.findById(id).map(ClientDTO::new).orElse( null);

    }


}

