package com.mindhub.AppHomeBanking.service;

import com.mindhub.AppHomeBanking.models.ClientLoan;

public interface ClientLoanService {

    void saveClientLoan(ClientLoan clientLoan);

    ClientLoan findById(Long id);
}
