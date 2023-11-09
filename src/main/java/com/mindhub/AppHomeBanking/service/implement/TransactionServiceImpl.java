package com.mindhub.AppHomeBanking.service.implement;

import com.mindhub.AppHomeBanking.models.Transaction;
import com.mindhub.AppHomeBanking.repositories.TransactionRepositories;
import com.mindhub.AppHomeBanking.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;

public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepositories transactionRepositories;

    @Override
    public void saveTransaction(Transaction transaction) {
        transactionRepositories.save(transaction);
    }
}
