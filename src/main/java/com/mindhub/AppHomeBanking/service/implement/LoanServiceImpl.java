package com.mindhub.AppHomeBanking.service.implement;

import com.mindhub.AppHomeBanking.dtos.LoanDTO;
import com.mindhub.AppHomeBanking.models.Loan;
import com.mindhub.AppHomeBanking.repositories.LoanRepositories;
import com.mindhub.AppHomeBanking.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class LoanServiceImpl implements LoanService {

    @Autowired
    private LoanRepositories loanRepositories;
    @Override
    public Loan findLoanById(Long id) {
        return loanRepositories.findById(id).orElse(null);
    }

    @Override
    public void saveLoan(Loan loan) {
        loanRepositories.save(loan);
    }

    @Override
    public Set<LoanDTO> findAllLoans() {
        return loanRepositories.findAll().stream().map(loandb -> new LoanDTO(loandb)).collect(Collectors.toSet());
    }
}
