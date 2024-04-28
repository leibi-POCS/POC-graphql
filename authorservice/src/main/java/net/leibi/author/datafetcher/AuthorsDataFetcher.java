package net.leibi.author.datafetcher;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.DgsDataFetchingEnvironment;
import com.netflix.graphql.dgs.DgsEntityFetcher;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import java.util.List;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import net.leibi.author.service.DataService;
import net.leibi.books.generated.types.Author;
import net.leibi.books.generated.types.Book;

@DgsComponent
@RequiredArgsConstructor
public class AuthorsDataFetcher
{

  private final DataService dataService;

  @DgsQuery
  public List<Author> author(@InputArgument String titleFilter) {
    if (titleFilter == null) {
      return dataService.getData();
    }

    return dataService.getData().stream().filter(s -> s.getName().contains(titleFilter)).toList();
  }

  @DgsQuery
  public Author bookById(@InputArgument String id) {
    return dataService.getAuthorById(id);
  }

  @DgsEntityFetcher(name = "Show")
  public Book book(Map<String, Object> values) {
    return new Book((String) values.get("id"), null);
  }

  @DgsData(parentType = "Book", field = "author")
  public Author authorFetcher(DgsDataFetchingEnvironment dataFetchingEnvironment)  {
    Book book = dataFetchingEnvironment.getSource();
    return dataService.getAuthorByBookId(book.getId());
  }

}
