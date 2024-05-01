package net.leibi.author.service;

import net.leibi.books.generated.types.Author;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DataServiceImpl implements DataService {

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
    @Cacheable(value = "authorsByIdCache")
    public Author getAuthorById(String id) {
        return dataSet.stream().filter(author -> author.getId().equals(id)).findFirst().orElseThrow();
    }

    @Override
    @Cacheable(value = "authorsCache")
    public Author getAuthorByName(String name) {
        return dataSet.stream()
                .filter(author -> author.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow();
    }

    @Override
    @Cacheable(value = "authorByBookIdCache")
    public Author getAuthorByBookId(String id) {
        return dataSet.stream()
                .filter(author -> author.getId().startsWith(id.substring(1, 3)))
                .findFirst()
                .orElseThrow();
    }

    @Override
    @Cacheable(value = "authorsFilteredCache")
    public List<Author> getAuthorsFiltered(String nameFilter) {
        return dataSet.stream().filter(author -> author.getName().contains(nameFilter)).toList();
    }
}
