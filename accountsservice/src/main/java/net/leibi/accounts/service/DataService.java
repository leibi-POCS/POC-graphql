package net.leibi.accounts.service;


import net.leibi.accounts.generated.types.Account;
import net.leibi.accounts.generated.types.AccountsByBank;

import java.util.List;

public interface DataService {
    List<Account> getData();

    void add(Account account);

    void add(List<Account> data);

    Account getAccountById(String id);

    Account getRandomAccount();

    AccountsByBank getAccountsByBankByBic(String bic);

    List<Account> getAccountsByBic(String bic, Integer page, Integer pageSize);
}
