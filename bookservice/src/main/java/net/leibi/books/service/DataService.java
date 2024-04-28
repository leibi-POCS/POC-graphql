package net.leibi.books.service;

import java.util.List;
import java.util.UUID;

import net.leibi.books.generated.types.Book;

public sealed interface DataService permits DataServiceImpl {
    List<Book> getData();
    void add(Book book);

    void add(List<Book> data);

    Book getBookById(String id);
}
