package net.leibi.books.data;

import java.util.UUID;

public record Book(UUID id, String name, Integer pageCount){
}
