package net.leibi.accounts;

import graphql.execution.instrumentation.Instrumentation;
import graphql.execution.instrumentation.tracing.TracingInstrumentation;
import jakarta.annotation.PostConstruct;
import lombok.extern.log4j.Log4j2;
import net.leibi.accounts.generated.types.Account;
import net.leibi.accounts.generated.types.Bank;
import net.leibi.accounts.service.DataService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.IntStream;

@SpringBootApplication
@Log4j2
public class AccountServiceApplication {
    private final DataService dataService;

    Map<Integer, Bank> bankCache = new HashMap<>();

    @Value("${application.upperbound:1000}")
    private int upperBound;

    public AccountServiceApplication(DataService dataService) {
        this.dataService = dataService;
    }

    public static void main(String[] args) {
        SpringApplication.run(AccountServiceApplication.class, args);
    }

    @PostConstruct
    public void init() {
        List<Account> data = getRandomData();
        dataService.add(data);
    }

    @Bean
    @ConditionalOnProperty(prefix = "graphql.tracing", name = "enabled", matchIfMissing = true)
    public Instrumentation tracingInstrumentation() {
        return new TracingInstrumentation();
    }

    private List<Account> getRandomData() {
        log.info("Creating {} random accounts", upperBound);
        return IntStream.range(1, upperBound)
                .mapToObj(this::getAccount)
                .toList();
    }

    private @NotNull Account getAccount(Integer i) {
        String s = String.valueOf(i);
        log.debug("Creating account {}", s);
        Bank bank = getBank(i);
        return new Account(UUID.randomUUID().toString(), s, s, s, bank);
    }

    private Bank getBank(Integer accountNumber) {
        Integer bankNumber = accountNumber % 100;
        return bankCache.computeIfAbsent(bankNumber, currentBankNumber -> {
            String s = String.valueOf(currentBankNumber % 100);
            log.info("Creating bank {}", s);
            return new Bank(UUID.randomUUID().toString(), s, s, s, s);
        });
    }
}
