package net.leibi.books.datafetcher;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import net.leibi.books.data.Book;
import net.leibi.books.service.DataService;

@DgsComponent
@RequiredArgsConstructor
public class BookDataFetcher {

  private final DataService dataService;

  @DgsQuery
  public List<Book> books(@InputArgument String titleFilter) {
    if (titleFilter == null) {
      return dataService.getData();
    }

    return dataService.getData().stream()
        .filter(s -> s.name().contains(titleFilter)).toList();
  }

  @DgsQuery
  public Book bookById(@InputArgument String id) {
    return dataService.getBookById(UUID.fromString(id));
  }
}
