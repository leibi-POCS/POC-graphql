package net.leibi.books.service;

import net.leibi.books.data.Book;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public sealed interface DataService permits DataServiceImpl {
    List<Book> getData();
    void add(Book book);

    void add(List<Book> data);

    Book getBookById(UUID id);
}
