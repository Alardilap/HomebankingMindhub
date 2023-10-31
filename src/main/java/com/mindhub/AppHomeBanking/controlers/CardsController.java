package com.mindhub.AppHomeBanking.controlers;

import com.mindhub.AppHomeBanking.dtos.AccountDTO;
import com.mindhub.AppHomeBanking.dtos.CardDTO;
import com.mindhub.AppHomeBanking.models.*;

import com.mindhub.AppHomeBanking.repositories.CardRepositories;
import com.mindhub.AppHomeBanking.repositories.ClientRepositories;
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
@RequestMapping("api")
public class CardsController {

    LocalDate date = LocalDate.now();

@Autowired
private CardRepositories cardRepositories;

@Autowired
private ClientRepositories clientRepositories;



    public int getRandomNumber(int min, int max) {return (int) ((Math.random() * (max - min)) + min);}

    public String generateNumberCard() {
        StringBuilder cardNumber; //3973-4475-2239-2248
        do {
            cardNumber = new StringBuilder();
            for (int i = 0; i < 16; i++) {
                cardNumber.append(getRandomNumber(0, 9));
                if ((i + 1) % 4 == 0 && i != 15) cardNumber.append("-");
            }
        }
        while (cardRepositories.existsByNumber(cardNumber.toString()));
        return cardNumber.toString();
    }
//    String string = "hola";
//    string = string + " como estas";

    public String generateCvvCard() {
        StringBuilder cvvNumber;
            cvvNumber = new StringBuilder();
            for (byte i = 0; i <= 2; i++) {
                cvvNumber.append(getRandomNumber(0, 9));
            }
        return cvvNumber.toString();
    }

    @RequestMapping(path = "/clients/current/cards", method = RequestMethod.POST)
    public ResponseEntity<?> addCard(@RequestParam CardColor color,  @RequestParam CardType type, Authentication authentication) {

        String email = authentication.getName();
        Client client = clientRepositories.findByEmail(email);

        if(client.getCards().stream().filter(card-> card.getType().equals(type)).collect(Collectors.toSet()).size() ==3 ){

            return new ResponseEntity<>("It is not possible to create another card", HttpStatus.FORBIDDEN);
        }

            Card card = new Card();

            card.setCardHolder(client.getName()+" "+client.getLastName());
            card.setNumber(generateNumberCard());
            card.setCvv(generateCvvCard());
            card.setColor(color);
            card.setType(type);
            card.setFromDate(date);
            card.setThruDate(date.plusYears(5));

           client.addCard(card);
           cardRepositories.save(card);

            return new ResponseEntity<>("Card Created", HttpStatus.CREATED);
    }
    @GetMapping("/clients/current/cards")
    public Set<CardDTO> getCards(Authentication authentication) {
        Client client = clientRepositories.findByEmail(authentication.getName());
        Set<CardDTO> cardsDTOS = client.getCards().stream().map(card -> new CardDTO(card)).collect(Collectors.toSet());
        if(client != null && cardsDTOS != null) {
            return cardsDTOS;
        }else{
            return new HashSet<>();
        }
    }
}
