package net.leibi.accounts.service;


import net.leibi.accounts.generated.types.Account;
import net.leibi.accounts.generated.types.AccountsByBank;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public final class DataServiceImpl implements DataService {

    private final List<Account> dataSet = new ArrayList<>();
    @Value("${application.upperbound:1000}")
    private int upperBound;

    public List<Account> getData() {
        return List.copyOf(dataSet);
    }

    public void add(Account Account) {
        dataSet.add(Account);
    }

    @Override
    public void add(List<Account> data) {
        dataSet.addAll(data);
    }

    @Override
    public Account getAccountById(String id) {
        return dataSet.stream().filter(account -> account.getId().equals(id)).findFirst().orElseThrow();
    }

    @Override
    public Account getRandomAccount() {
        return dataSet.get(Random.from(new SecureRandom()).nextInt(upperBound - 1));
    }

    @Override
    public AccountsByBank getAccountsByBankByBic(String bic) {
        var accounts = getAccountsByBic(bic);
        return new AccountsByBank(accounts.getFirst().getBank(), accounts);
    }

    @Override
    public List<Account> getAccountsByBic(String bic) {
        return dataSet.stream().filter(account -> account.getBank().getBic().equalsIgnoreCase(bic)).toList();
    }
}
