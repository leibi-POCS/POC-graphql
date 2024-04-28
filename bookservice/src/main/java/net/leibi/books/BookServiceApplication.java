package net.leibi.books;

import jakarta.annotation.PostConstruct;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;
import lombok.extern.log4j.Log4j2;
import net.leibi.books.data.Book;
import net.leibi.books.service.DataService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Log4j2
public class BookServiceApplication {
  private final DataService dataService;

  @Value("${application.upperbound:1000}")
  private int upperBound;

  public BookServiceApplication(DataService dataService) {
    this.dataService = dataService;
  }

  public static void main(String[] args) {
    SpringApplication.run(BookServiceApplication.class, args);
  }

  @PostConstruct
  public void init() {
    List<Book> data = getRandomData();
    dataService.add(data);
  }

  private List<Book> getRandomData() {
    log.info("Creating {} random books", upperBound);
    return IntStream.range(1, upperBound)
        .mapToObj(i -> new Book(UUID.randomUUID(), String.valueOf(i), i))
        .toList();
  }
}
