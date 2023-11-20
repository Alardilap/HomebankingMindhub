package com.mindhub.AppHomeBanking;

import com.mindhub.AppHomeBanking.models.Loan;
import com.mindhub.AppHomeBanking.repositories.LoanRepositories;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class LoanRepositoryTest {
    @Autowired
    private LoanRepositories loanRepositories;

    Loan loanMortgage, loanStaff, loanAutomotive;

    @BeforeEach
    public void setUpLoan() {
        loanMortgage = loanRepositories.findByName("Mortgage");
        loanStaff = loanRepositories.findByName("Staff");
        loanAutomotive = loanRepositories.findByName("Automotive");
    }

    @Test
    public void existLoans() {
        List<Loan> loans = loanRepositories.findAll();
        assertThat(loans, is(not(empty())));
    }

    @Test
    public void existPersonalLoan() {
        List<Loan> loans = loanRepositories.findAll();
        assertThat(loans, hasItem(hasProperty("name", is("Mortgage"))));
        assertThat(loans, hasItem(hasProperty("name", is("Staff"))));
        assertThat(loans, hasItem(hasProperty("name", is("Automotive"))));
    }

    @Test
    public void checkLoanAmountByName() {
        assertNotNull(loanMortgage, "No loan found with the name: " + loanMortgage.getName());
        assertNotNull(loanStaff, "No loan found with the name: " + loanStaff.getName());
        assertNotNull(loanAutomotive, "No loan found with the name: " + loanAutomotive.getName());

        assertEquals(5000000.0, loanMortgage.getMaxAmount(), "Incorrect loan amount for: " + loanMortgage.getName());
        assertEquals(1000000, loanStaff.getMaxAmount(), "Incorrect loan amount for: " + loanStaff.getName());
        assertEquals(300000, loanAutomotive.getMaxAmount(), "Incorrect loan amount for: " + loanAutomotive.getName());
    }

    @Test
    public void checknumberofpayments() {
        List<Integer> expectedPaymentsMortgage = List.of(12, 24, 36, 48, 60);
        List<Integer> expectedPaymentsStaff = List.of(6, 12, 24);
        List<Integer> expectedPaymentsAutomotive = List.of(6, 12, 24, 36);

        assertNotNull(loanMortgage, "No loan found with the name: " + loanMortgage.getName());
        assertNotNull(loanStaff, "No loan found with the name: " + loanStaff.getName());
        assertNotNull(loanAutomotive, "No loan found with the name: " + loanAutomotive.getName());

        assertEquals(expectedPaymentsMortgage, loanMortgage.getPayments(), "Incorrect number payments for: " + loanMortgage.getName());
        assertEquals(expectedPaymentsStaff, loanStaff.getPayments(), "Incorrect number payments  for: " + loanStaff.getName());
        assertEquals(expectedPaymentsAutomotive, loanAutomotive.getPayments(), "Incorrect number payments for: " + loanAutomotive.getName());
    }
}
