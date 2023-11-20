package com.mindhub.AppHomeBanking.controlers;

import com.mindhub.AppHomeBanking.Utils.CardUtils;
import com.mindhub.AppHomeBanking.dtos.CardDTO;
import com.mindhub.AppHomeBanking.models.*;
import com.mindhub.AppHomeBanking.repositories.CardRepositories;
import com.mindhub.AppHomeBanking.service.CardService;
import com.mindhub.AppHomeBanking.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class CardsController {

    @Autowired
    private CardService cardService;

    @Autowired
    private ClientService clientService;

    public String generateNumberCard() {
        String cardNumber;
        do {
            cardNumber = CardUtils.generateCardNumber();
        }
        while (cardService.existsByNumber(cardNumber.toString()));
        return cardNumber.toString();
    }

    @PostMapping(path = "/clients/current/cards")
    public ResponseEntity<?> addCard(@RequestParam CardColor color, @RequestParam CardType type, Authentication authentication) {

        String email = authentication.getName();
        Client client = clientService.findClientByEmail(email);

        if(client.getCards().stream().filter(card -> card.getType()==type && card.getColor()==color && card.getActive()).count() >=1) {

            return new ResponseEntity<>("It is not possible to create another card", HttpStatus.FORBIDDEN);

        }

        Card card = new Card();

        card.setCardHolder(client.getName() + " " + client.getLastName());
        card.setNumber(generateNumberCard());
        card.setCvv(CardUtils.getCVV());
        card.setColor(color);
        card.setType(type);
        card.setFromDate(LocalDate.now());
        card.setThruDate(LocalDate.now().plusYears(5));
        client.addCard(card);
        cardService.saveCard(card);

        return new ResponseEntity<>("Card Created", HttpStatus.CREATED);
    }

    @GetMapping("/clients/current/cards")
    public Set<CardDTO> getCards(Authentication authentication) {
        Client client = clientService.findClientByEmail(authentication.getName());
        Set<CardDTO> cardsDTOS = client.getCards().stream().map(card -> new CardDTO(card)).filter(cardDTO -> cardDTO.getActive()==true).collect(Collectors.toSet());
        if (client != null && cardsDTOS != null) {
            return cardsDTOS;
        } else {
            return new HashSet<>();
        }
    }

    @PatchMapping("/cards/modify")
    public Object cardModify(@RequestParam Boolean active, @RequestParam Long id, Authentication authentication){

        Client client = clientService.findClientByEmail(authentication.getName());
    Card modifyCard = cardService.findByIdAndClientCards(id,client);


    if(modifyCard==null){
       return null;
    }

        modifyCard.setActive(active);
        cardService.saveCard(modifyCard);
        clientService.saveClient(client);
     return new CardDTO(modifyCard);
    }
}