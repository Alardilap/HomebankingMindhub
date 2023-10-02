package com.mindhub.AppHomeBanking.repositories;

import com.mindhub.AppHomeBanking.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;

//REST REPOSITORY QUIEN TRABAJA BAJO LOS LINEAMIENTOS DE REST,ESCUCHA PETICIONES HTTP, VA A COMUNICAR A JPA REPOSITORY LA PETICON QUE LLEGO.
//*JPA ESCUCHA LO QUE RECIBE DE REST REPOSITORY Y SE COMUNICA CON LA BD Y OBTIENE LA INFO. LE PASA ESTOS DATOS A REST REPOSITORY Y ESTE DEVUELVE LA INFO AL USUARIO.
public interface ClientRepositories extends JpaRepository <Client, Long>{



}
