package net.leibi.transactions.service;


import net.leibi.transactions.generated.types.Transaction;

import java.util.List;

public interface TransactionDataService {
    List<Transaction> getData();

    void add(Transaction transaction);

    void add(List<Transaction> data);

    Transaction getTransactionById(String id);

    List<Transaction> getTransactionsByAccountId(String accountId);

    List<Transaction> getTransactionsByMinAmount(Double minAmount);
}
