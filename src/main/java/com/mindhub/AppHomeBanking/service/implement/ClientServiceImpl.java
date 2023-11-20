package com.mindhub.AppHomeBanking.service.implement;

import com.mindhub.AppHomeBanking.dtos.ClientDTO;
import com.mindhub.AppHomeBanking.models.Client;
import com.mindhub.AppHomeBanking.repositories.ClientRepositories;
import com.mindhub.AppHomeBanking.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientRepositories clientRepositories;
    @Override
    public List<Client> getAllClients() {
        return clientRepositories.findAll();
    }

    @Override
    public Set<ClientDTO> getAllClientsDTO() {
      return clientRepositories.findAll().stream().map(client -> new ClientDTO(client)).collect(Collectors.toSet());
    }

    @Override
    public ClientDTO findClientById(Long id) {
        return clientRepositories.findById(id).map(ClientDTO::new).orElse(null);
    }

    @Override
    public void saveClient(Client client) {
     clientRepositories.save(client);
    }

    @Override
    public Client findClientByEmail(String email) {
        return clientRepositories.findByEmail(email);
    }

    @Override
    public boolean existsClientByEmail(String email) {
        return clientRepositories.existsByEmail(email);
    }

//    @Override
//    public Client findClientAuthentiByEmail(String email) {
//        return clientRepositories.findByEmail(email);
//    }

}
