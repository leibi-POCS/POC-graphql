package net.leibi.transactions.datafetcher;

import io.micrometer.observation.annotation.Observed;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.leibi.transactions.generated.types.Account;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
@Observed
public class AccountsDataFetcher {
  private final AccountsApi accountsApi;

  private final Map<Integer, Account> cache = new HashMap<>();

  @Cacheable("accountsById")
  public Account getAccountById(String accountId) {
    return accountsApi.getAccountBy(accountId);
  }

  @Cacheable("randomAccountInFetcher")
  public Account getRandomAccount() {
    return accountsApi.getRandomAccount();
  }
}
