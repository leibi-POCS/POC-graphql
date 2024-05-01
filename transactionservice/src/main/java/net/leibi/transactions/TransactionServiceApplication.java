package net.leibi.transactions;

import graphql.execution.instrumentation.Instrumentation;
import graphql.execution.instrumentation.tracing.TracingInstrumentation;
import jakarta.annotation.PostConstruct;
import lombok.extern.log4j.Log4j2;
import net.leibi.transactions.generated.types.Transaction;
import net.leibi.transactions.service.AccountService;
import net.leibi.transactions.service.TransactionDataService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

@SpringBootApplication
@Log4j2
@EnableCaching
@EnableFeignClients
public class TransactionServiceApplication {
    private final TransactionDataService transactionDataService;
    private final AccountService accountService;

    @Value("${application.upperbound:1000}")
    private int upperBound;

    public TransactionServiceApplication(TransactionDataService transactionDataService, AccountService accountService) {
        this.transactionDataService = transactionDataService;
        this.accountService = accountService;
    }

    public static void main(String[] args) {
        SpringApplication.run(TransactionServiceApplication.class, args);
    }

    @PostConstruct
    public void init() {
        List<Transaction> data = getRandomData();
        transactionDataService.add(data);
    }

    @Bean
    @ConditionalOnProperty(prefix = "graphql.tracing", name = "enabled", matchIfMissing = true)
    public Instrumentation tracingInstrumentation() {
        return new TracingInstrumentation();
    }

    private List<Transaction> getRandomData() {
        log.info("Creating {} random Transactions", upperBound);
        return IntStream.range(1, upperBound)
                .parallel()
                .mapToObj(this::getTransaction)
                .toList();
    }

    private @NotNull Transaction getTransaction(int i) {
        final var bookingText = String.valueOf(i);
        var reducedNumber = i % 100;
        var accountId = accountService.getRandomAccount(reducedNumber).getId();
        var id = UUID.randomUUID().toString();
        return new Transaction(id, bookingText, accountId, (double) i);
    }
}
