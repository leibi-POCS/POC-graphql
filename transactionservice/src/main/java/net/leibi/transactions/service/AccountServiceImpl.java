package net.leibi.transactions.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.leibi.books.generated.types.Account;
import net.leibi.transactions.datafetcher.AccountsDataFetcher;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class AccountServiceImpl implements AccountService {

    private final AccountsDataFetcher accountsDataFetcher;

    @Override
    @Cacheable("accountById")
    public Account getAccountById(String id) {
        return accountsDataFetcher.getAccountById(id);
    }

    @Override
    @Cacheable("randomAccountCache")
    public Account getRandomAccount(Integer i) {
        log.debug("Getting account with {}", i);
        return accountsDataFetcher.getRandomAccount(i);
    }
}
