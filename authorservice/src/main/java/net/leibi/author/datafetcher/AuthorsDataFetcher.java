package net.leibi.author.datafetcher;

import com.netflix.graphql.dgs.*;
import lombok.RequiredArgsConstructor;
import net.leibi.accounts.generated.types.Author;
import net.leibi.accounts.generated.types.Book;
import net.leibi.author.service.DataService;

import java.util.List;
import java.util.Map;

@DgsComponent
@RequiredArgsConstructor
public class AuthorsDataFetcher {

    private final DataService dataService;

    @DgsQuery
    public List<Author> authors(@InputArgument String nameFilter) {
        if (nameFilter == null) {
            return dataService.getData();
        }

        return dataService.getAuthorsFiltered(nameFilter);
    }

    @DgsQuery
    public Author authorById(@InputArgument String id) {
        return dataService.getAuthorById(id);
    }

    @DgsEntityFetcher(name = "Book")
    public Book book(Map<String, Object> values) {
        return new Book((String) values.get("id"), null);
    }

    @DgsData(parentType = "Book", field = "author")
    public Author authorFetcher(DgsDataFetchingEnvironment dataFetchingEnvironment) {
        Book book = dataFetchingEnvironment.getSource();
        return dataService.getAuthorByBookId(book.getId());
    }

}
