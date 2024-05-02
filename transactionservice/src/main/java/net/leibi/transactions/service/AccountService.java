package net.leibi.transactions.service;


import java.util.Optional;

import net.leibi.transactions.generated.types.Account;

public interface AccountService {

    Account getAccountById(String id);

    Optional<Account> getRandomAccount();

}
