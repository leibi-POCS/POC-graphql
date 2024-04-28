package net.leibi.books.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import net.leibi.books.generated.types.Book;

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
  public Book getBookById(String id) {
    return dataSet.stream().filter(book -> book.getId().equals(id)).findFirst().orElseThrow();
  }
}
