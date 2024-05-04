package net.leibi.transactions.service;

import io.micrometer.observation.annotation.Observed;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.leibi.transactions.datafetcher.AccountsDataFetcher;
import net.leibi.transactions.generated.types.Account;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
@Log4j2
@Observed
public class AccountServiceImpl implements AccountService {

    public static final Random RANDOM = new Random();
    private final AccountsDataFetcher accountsDataFetcher;

    @Value("${application.number.accounts:1000}")
    private int numberAccounts;

    List<Account> accounts = new ArrayList<>(numberAccounts);
    List<Integer> alreadySeenAccounts = new ArrayList<>(numberAccounts);

    @Override
    @Cacheable("accountById")
    public Account getAccountById(String id) {
        return accountsDataFetcher.getAccountById(id);
    }

    @Override
    public Optional<Account> getRandomAccount() {

        if (accounts.isEmpty()) {
            fillAccounts();
            log.info("Account list is filled now");
        }

        final int newIndex = alreadySeenAccounts.isEmpty() ? 0 : alreadySeenAccounts.getLast() + 1;

        if (newIndex < accounts.size()) {
            alreadySeenAccounts.add(newIndex);
            return Optional.of(accounts.get(newIndex));
        } else {
            alreadySeenAccounts.clear();
            return getRandomAccount();
        }
    }

    private void fillAccounts() {
        log.info("Creating {} random accounts", numberAccounts);
        accounts =
                IntStream.range(0, numberAccounts)
                        .mapToObj(j -> accountsDataFetcher.getRandomAccount())
                        .toList();
    }

}
