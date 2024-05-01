package net.leibi.transactions.service;


import net.leibi.transactions.generated.types.Account;

public interface AccountService {

    Account getAccountById(String id);

    Account getRandomAccount(Integer i);

}
