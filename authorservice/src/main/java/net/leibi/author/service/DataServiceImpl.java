package net.leibi.author.service;

import java.util.ArrayList;
import java.util.List;
import net.leibi.books.generated.types.Author;
import org.springframework.stereotype.Service;

@Service
public final class DataServiceImpl implements DataService {

  private final List<Author> dataSet = new ArrayList<>();

  @Override
  public List<Author> getData() {
    return dataSet;
  }

  @Override
  public void add(Author author) {
    dataSet.add(author);
  }

  @Override
  public void add(List<Author> data) {
    dataSet.addAll(data);
  }

  @Override
  public Author getAuthorById(String id) {
    return dataSet.stream().filter(author -> author.getId().equals(id)).findFirst().orElseThrow();
  }

  @Override
  public Author getAuthorByName(String name) {
    return dataSet.stream()
        .filter(author -> author.getName().contains(name))
        .findFirst()
        .orElseThrow();
  }

  @Override
  public Author getAuthorByBookId(String id) {
    return dataSet.stream()
        .filter(author -> author.getId().startsWith(id.substring(1, 3)))
        .findFirst()
        .orElseThrow();
  }
}
