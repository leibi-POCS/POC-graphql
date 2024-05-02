package net.leibi.transactions.service;

import io.micrometer.observation.annotation.Observed;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.leibi.transactions.datafetcher.AccountsDataFetcher;
import net.leibi.transactions.generated.types.Account;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
@Observed
public class AccountServiceImpl implements AccountService {

  public static final Random random = new Random();
  private final AccountsDataFetcher accountsDataFetcher;

  @Value("${application.number.accounts:1000}")
  private int numberAccounts;

  List<Account> accounts = new ArrayList<>(numberAccounts);
  Set<Integer> alreadySeenAccounts = new HashSet<>(numberAccounts);

  @Override
  @Cacheable("accountById")
  public Account getAccountById(String id) {
    return accountsDataFetcher.getAccountById(id);
  }

  @Override
  @Cacheable("randomAccountinService")
  public Optional<Account> getRandomAccount() {

    if (accounts.isEmpty()) {
      fillAccounts();
    }

    int index = getRandomNumber(0, numberAccounts - 1);
    int cnt = 0;
    while (alreadySeenAccounts.contains(index)) {
      index = getRandomNumber(0, numberAccounts - 1);
      cnt++;
      if (cnt > numberAccounts) {
        log.error("Could not find a new account");
        return Optional.empty();
      }
    }
    // log.debug("Returning account at index {}", index);
    return Optional.of(accounts.get(index));
  }

  private void fillAccounts() {
    log.info("Creating {} random accounts", numberAccounts);
    accounts =
        IntStream.range(0, numberAccounts)
            .mapToObj(j -> accountsDataFetcher.getRandomAccount())
            .toList();
  }

  private int getRandomNumber(int min, int max) {
    return (random.nextInt() * (max - min)) + min;
  }
}
