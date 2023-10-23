package com.mindhub.AppHomeBanking.services;

import com.mindhub.AppHomeBanking.models.Client;
import com.mindhub.AppHomeBanking.repositories.ClientRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CreateClient {
        @Autowired
        private ClientRepositories clientRepositories;

        @Autowired
        private PasswordEncoder passwordEncoder;

        public Client createClient( String name, String lastName, String email, String password) {
            // Verifica si el email ya está en uso
            if (clientRepositories.findByEmail(email) != null) {
                throw new RuntimeException("El correo electrónico ya está en uso.");
            }

           Client client = new Client();
            client.setName(name);
            client.setLastName(lastName);
            client.setEmail(email);
            client.setPassword(passwordEncoder.encode(password));

            return clientRepositories.save(client); // Guarda el usuario en la base de datos
        }
    }

