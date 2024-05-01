package net.leibi.accounts.service;


import net.leibi.accounts.generated.types.Account;

import java.util.List;

public sealed interface DataService permits DataServiceImpl {
    List<Account> getData();

    void add(Account account);

    void add(List<Account> data);

    Account getAccountById(String id);

    Account getRandomAccount();
}
