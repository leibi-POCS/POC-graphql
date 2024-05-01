package net.leibi.transactions.service;

import net.leibi.books.generated.types.Account;

public interface AccountService {

    Account getAccountById(String id);
}
