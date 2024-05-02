package net.leibi.transactions;

import io.micrometer.observation.ObservationRegistry;
import io.micrometer.observation.annotation.Observed;
import jakarta.annotation.PostConstruct;
import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.stream.IntStream;
import lombok.extern.log4j.Log4j2;
import net.leibi.transactions.generated.types.Account;
import net.leibi.transactions.generated.types.Transaction;
import net.leibi.transactions.service.AccountService;
import net.leibi.transactions.service.TransactionDataService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@Log4j2
@EnableCaching
@EnableFeignClients
@Observed
public class TransactionServiceApplication {
  public static final Random RANDOMFROM = new Random();
  private final TransactionDataService transactionDataService;
  private final AccountService accountService;
  private final ObservationRegistry observationRegistry;

  @Value("${application.upperbound:1000}")
  private int upperBound;

  public TransactionServiceApplication(
      TransactionDataService transactionDataService,
      AccountService accountService,
      ObservationRegistry observationRegistry) {
    this.transactionDataService = transactionDataService;
    this.accountService = accountService;
    this.observationRegistry = observationRegistry;
  }

  public static void main(String[] args) {
    SpringApplication.run(TransactionServiceApplication.class, args);
  }

  @PostConstruct
  public void init() {
    /*
           List<Transaction> data = Observation
                   .createNotStarted("getRandomTransactions", observationRegistry)
                   .observe(this::getRandomData);
       assert (data != null);
    */
    List<Transaction> data = getRandomData();

    log.info("got {} transactions", data.size());
    transactionDataService.add(data);
  }

  private List<Transaction> getRandomData() {
    log.info("Creating {} random Transactions", upperBound);
    return IntStream.range(1, upperBound).mapToObj(this::getTransaction).toList();
  }

  private @NotNull Transaction getTransaction(int i) {
    if (i % 100_000 == 0) {
      log.info("getTransaction {}", i);
    }
    final var bookingText = String.valueOf(i);
    Optional<Account> randomAccount = accountService.getRandomAccount();
    if (randomAccount.isEmpty()) {
      log.error("Could not find a random account");
      throw new RuntimeException("Could not find a random account");
    }
    var accountId = randomAccount.get().getId();
    var id = UUID.randomUUID().toString();
    var amount = Double.valueOf(RANDOMFROM.nextFloat(1000, 1000000));
    return new Transaction(id, bookingText, accountId, amount);
  }
}
