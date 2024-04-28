package net.leibi.books.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import net.leibi.books.data.Book;
import org.springframework.stereotype.Service;

@Service
public final class DataServiceImpl implements DataService {

  private final List<Book> dataSet = new ArrayList<>();

  public List<Book> getData() {
    return List.copyOf(dataSet);
  }

  public void add(Book book) {
    dataSet.add(book);
  }

  @Override
  public void add(List<Book> data) {
    dataSet.addAll(data);
  }

  @Override
  public Book getBookById(UUID id) {
    return dataSet.stream().filter(book -> book.id().equals(id)).findFirst().orElseThrow();
  }
}
