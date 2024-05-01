package net.leibi.transactions.datafetcher;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.leibi.transactions.generated.types.Account;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class AccountsDataFetcher {

    private final AccountsApi accountsApi;

    @Cacheable("accountsById")
    public Account getAccountById(String accountId) {
        return accountsApi.getAccountBy(accountId);
    }

    @Cacheable("randomAccountInFetcher")
    public Account getRandomAccount(Integer accountRelatedId) {
        log.info("get random account for {}", accountRelatedId);
        return accountsApi.getRandomAccount();
    }
}
