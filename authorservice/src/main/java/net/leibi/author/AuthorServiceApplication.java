package net.leibi.author;

import graphql.execution.instrumentation.Instrumentation;
import graphql.execution.instrumentation.tracing.TracingInstrumentation;
import jakarta.annotation.PostConstruct;
import lombok.extern.log4j.Log4j2;
import net.leibi.author.service.DataService;
import net.leibi.books.generated.types.Author;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

@SpringBootApplication
@Log4j2
@EnableCaching
public class AuthorServiceApplication {
    private final DataService dataService;

    @Value("${application.upperbound:1000}")
    private int upperBound;

    public AuthorServiceApplication(DataService dataService) {
        this.dataService = dataService;
    }

    public static void main(String[] args) {
        SpringApplication.run(AuthorServiceApplication.class, args);
    }

    @PostConstruct
    public void init() {
        List<Author> data = getRandomData();
        dataService.add(data);
    }

    @Bean
    @ConditionalOnProperty(prefix = "graphql.tracing", name = "enabled", matchIfMissing = true)
    public Instrumentation tracingInstrumentation() {
        return new TracingInstrumentation();
    }

    private List<Author> getRandomData() {
        log.info("Creating {} random Authors", upperBound);
        return IntStream.range(1, upperBound)
                .mapToObj(i -> new Author(UUID.randomUUID().toString(), String.valueOf(i)))
                .toList();
    }
}
