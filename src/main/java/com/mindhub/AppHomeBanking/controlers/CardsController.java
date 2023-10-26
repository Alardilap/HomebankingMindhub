package com.mindhub.AppHomeBanking.controlers;

import com.mindhub.AppHomeBanking.models.*;

import com.mindhub.AppHomeBanking.repositories.CardRepositories;
import com.mindhub.AppHomeBanking.repositories.ClientRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api")
public class CardsController {

    LocalDate date = LocalDate.now();

@Autowired
private CardRepositories cardRepositories;

@Autowired
private ClientRepositories clientRepositories;




    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
    public String generateNumberCard() {
        StringBuilder cardNumber; //3973-4475-2239-2248
        do {
            cardNumber = new StringBuilder();
            for (int i = 0; i < 16; i++) {
                cardNumber.append(getRandomNumber(0, 9));
                if ((i + 1) % 4 == 0 && i != 15) cardNumber.append("-");
            }
        } while (cardRepositories.existsByNumber(cardNumber.toString()));
        return cardNumber.toString();
    }

    public String generateCvvCard() {
        StringBuilder cvvNumber;
        do {
            cvvNumber = new StringBuilder();
            for (byte i = 0; i <= 2; i++) {
                cvvNumber.append(getRandomNumber(0, 9));
            }
        } while (cardRepositories.existsByCvv(cvvNumber.toString()));
        return cvvNumber.toString();
    }

    @RequestMapping(path = "/clients/current/cards", method = RequestMethod.POST)
    public ResponseEntity<?> addCard(@RequestParam CardColor color,  @RequestParam CardType type, Authentication authentication) {

        String email = authentication.getName();
        Client client = clientRepositories.findByEmail(email);

        if(client == null){
            return new ResponseEntity<>("this request is not possible", HttpStatus.FORBIDDEN);
        }
        if(client.getCards().stream().filter(card-> card.getType()==CardType.CREDIT).collect(Collectors.toSet()).size() ==2 ){

            return new ResponseEntity<>("It is not possible to create another card", HttpStatus.FORBIDDEN);
        }
        if(client.getCards().stream().filter(card-> card.getType()==CardType.DEBIT).collect(Collectors.toSet()).size() ==2 ){

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
}
