package net.leibi.author.service;

import net.leibi.books.generated.types.Author;

import java.util.List;

public interface DataService {
    List<Author> getData();

    void add(Author author);

    void add(List<Author> data);

    Author getAuthorById(String id);

    Author getAuthorByName(String name);

    Author getAuthorByBookId(String id);

    List<Author> getAuthorsFiltered(String nameFilter);
}
