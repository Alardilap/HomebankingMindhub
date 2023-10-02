package com.mindhub.AppHomeBanking.controlers;


import com.mindhub.AppHomeBanking.models.Client;
import com.mindhub.AppHomeBanking.repositories.ClientRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/clients")
public class ClientController {


    @Autowired
    private ClientRepositories clientRepositories;
    @PostMapping("/post") // Ruta para la solicitud POST de creación de cliente
    public Client addClient (@RequestBody Client client) {
        return clientRepositories.save(client);
    }


}
