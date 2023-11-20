package com.mindhub.AppHomeBanking;

import com.mindhub.AppHomeBanking.models.Account;
import com.mindhub.AppHomeBanking.models.Card;
import com.mindhub.AppHomeBanking.models.CardColor;
import com.mindhub.AppHomeBanking.models.Client;
import com.mindhub.AppHomeBanking.repositories.CardRepositories;
import com.mindhub.AppHomeBanking.repositories.LoanRepositories;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.fail;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CardRespositoryTest {

    @Autowired
    private CardRepositories cardRepositories;

    List<Card> card;
    @BeforeEach
    public void sepUpCard(){
        card = cardRepositories.findAll();
    }

    @Test
    public void existCards() {
        assertThat(card, is(not(empty())));
    }

    @Test
    public void numberCard(){
    String regexNumber=  "^\\d{4}-\\d{4}-\\d{4}-\\d{4}$";

        List<Card> invalidAccountNumber = card.stream()
                .filter(account -> !account.getNumber().matches(regexNumber))
                .collect(Collectors.toList());

        if (!invalidAccountNumber.isEmpty()) {
            for (Card card : invalidAccountNumber) {
                System.out.println("The Card con el id " + card.getId() + " has an invalid card number: " + card.getNumber());
            }
            throw new AssertionError("There are accounts with an invalid card number");
        } else {
            System.out.println("All card are valid with respect to their account number");
        }
    }

    @Test
    public void cvv(){
        int maxDigitsCvv = 3;

        List<Card> exceededDigitsCvv= card.stream()
                .filter(card -> card.getCvv().length() > maxDigitsCvv)
                .collect(Collectors.toList());

        if (!exceededDigitsCvv.isEmpty()) {
            for (Card card : exceededDigitsCvv) {
                System.out.println("The Card with id: " + card.getId() + " exceeded the number of digits allowed: " + card.getCvv());
            }
            throw new AssertionError("Some cards do not have valid cvv.");
        } else {
            System.out.println("There are no cards with invalid CVV");
        }
    }
}
