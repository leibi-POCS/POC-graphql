package net.leibi.author.service;

import java.util.List;
import java.util.UUID;

import net.leibi.books.generated.types.Author;
import net.leibi.books.generated.types.Book;

public sealed interface DataService permits net.leibi.author.service.DataServiceImpl
{
    List<Author> getData();
    void add(Author author);
    void add(List<Author> data);

    Author getAuthorById(String id);
    Author getAuthorByName(String name);

    Author getAuthorByBookId(String id);
}
