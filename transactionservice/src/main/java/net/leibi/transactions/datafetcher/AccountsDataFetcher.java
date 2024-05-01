package net.leibi.transactions.datafetcher;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.leibi.transactions.generated.types.Account;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Log4j2
public class AccountsDataFetcher {
    private final AccountsApi accountsApi;

    private final Map<Integer, Account> cache = new HashMap<>();

    @Cacheable("accountsById")
    public Account getAccountById(String accountId) {
        return accountsApi.getAccountBy(accountId);
    }

    @Cacheable("randomAccountInFetcher")
    public Account getRandomAccount(Integer accountRelatedId) {
        return cache.computeIfAbsent(accountRelatedId, id -> {
            log.info("get random account for {}", id);
            return accountsApi.getRandomAccount();
        });
    }
}
