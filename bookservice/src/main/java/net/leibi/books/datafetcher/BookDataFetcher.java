package net.leibi.books.datafetcher;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import java.util.List;
import lombok.RequiredArgsConstructor;
import net.leibi.books.generated.types.Book;
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

    return dataService.getData().stream().filter(s -> s.getName().contains(titleFilter)).toList();
  }

  @DgsQuery
  public Book bookById(@InputArgument String id) {
    return dataService.getBookById(id);
  }
}
