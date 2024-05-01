package net.leibi.transactions.service;


import net.leibi.transactions.generated.types.Transaction;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TransactionDataServiceImpl implements TransactionDataService {

    private final Map<String, List<Transaction>> mapAccountIdsToTransactions = new HashMap<>();

    @Override
    public List<Transaction> getData() {
        return mapAccountIdsToTransactions.values().stream().flatMap(List::stream).toList();
    }

    @Override
    public void add(Transaction transaction) {
        mapAccountIdsToTransactions
                .computeIfAbsent(transaction.getAccountid(), k -> new ArrayList<>())
                .add(transaction);

    }

    @Override
    public void add(List<Transaction> data) {
        data.forEach(this::add);
    }

    @Override
    public Transaction getTransactionById(String id) {
        return mapAccountIdsToTransactions.values().
                stream()
                .flatMap(List::stream)
                .filter(transaction -> transaction.getId().equals(id))
                .findFirst().orElseThrow();
    }

    @Override
    public List<Transaction> getTransactionsByAccountId(String accountId) {
        return mapAccountIdsToTransactions.get(accountId);
    }

    @Override
    public List<Transaction> getTransactionsByMinAmount(Double minAmount) {
        return mapAccountIdsToTransactions.values()
                .stream().flatMap(List::stream)
                .filter(transaction -> transaction.getAmount() > minAmount)
                .toList();
    }
}
