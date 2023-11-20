package com.mindhub.AppHomeBanking.service;

import com.mindhub.AppHomeBanking.dtos.ClientDTO;
import com.mindhub.AppHomeBanking.models.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.Set;

public interface ClientService {

    List<Client> getAllClients();
    Set<ClientDTO> getAllClientsDTO();
    ClientDTO findClientById(Long id);
    void saveClient(Client client);
    Client findClientByEmail(String email);
    boolean existsClientByEmail(String email);
//  Client findClientAuthentiByEmail(String email);
}
