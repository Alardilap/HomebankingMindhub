package com.mindhub.AppHomeBanking.service.implement;

import com.mindhub.AppHomeBanking.models.ClientLoan;
import com.mindhub.AppHomeBanking.repositories.ClientLoanRepositories;
import com.mindhub.AppHomeBanking.service.ClientLoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientLoanServiceImpl implements ClientLoanService {

    @Autowired
    private ClientLoanRepositories clientLoanRepositories;
    @Override
    public void saveClientLoan(ClientLoan clientLoan) {
        clientLoanRepositories.save(clientLoan);
    }

    @Override
    public ClientLoan findById(Long id) {
        return clientLoanRepositories.findById(id).orElse(null);
    }
}
