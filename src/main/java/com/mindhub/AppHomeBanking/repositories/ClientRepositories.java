package com.mindhub.AppHomeBanking.repositories;
//JPA REST REPOSITORY
import com.mindhub.AppHomeBanking.models.Client;
import com.mindhub.AppHomeBanking.models.ClientLoan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

//REST REPOSITORY QUIEN TRABAJA BAJO LOS LINEAMIENTOS DE REST,ESCUCHA PETICIONES HTTP, VA A COMUNICAR A JPA REPOSITORY LA PETICON QUE LLEGO.
//*JPA ESCUCHA LO QUE RECIBE DE REST REPOSITORY Y SE COMUNICA CON LA BD Y OBTIENE LA INFO. LE PASA ESTOS DATOS A REST REPOSITORY Y ESTE DEVUELVE LA INFO AL USUARIO.@
@RepositoryRestResource
public interface ClientRepositories extends JpaRepository <Client, Long>{
   Client findByEmail(String email);
//      Este método tiene un nombre específico: findByEmail, y se utiliza para definir
//   una consulta personalizada que busca un objeto de tipo Client
//   en la base de datos por su dirección de correo electrónico (email).
}
