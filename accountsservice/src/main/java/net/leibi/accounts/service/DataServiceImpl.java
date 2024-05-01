package net.leibi.accounts.service;

import net.leibi.books.generated.types.Account;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public final class DataServiceImpl implements DataService {

    private final List<Account> dataSet = new ArrayList<>();

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
        return dataSet.stream().filter(Account -> Account.getId().equals(id)).findFirst().orElseThrow();
    }
}
