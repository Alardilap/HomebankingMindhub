package com.mindhub.AppHomeBanking.service;

import com.mindhub.AppHomeBanking.dtos.LoanDTO;
import com.mindhub.AppHomeBanking.models.Loan;

import java.util.Set;

public interface LoanService {

    Loan findLoanById(Long id);
    void saveLoan(Loan loan);
    Set<LoanDTO> findAllLoans();
}
