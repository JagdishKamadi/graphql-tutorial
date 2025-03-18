package com.graphql.graphql_demo.model;

import java.util.Arrays;
import java.util.List;

public record Book(Integer id, String name, Integer price, Author author) {

    private static List<Book> books = Arrays.asList(
            new Book(1, "Drilling in c", 100, new Author(1, "Jagdish Kamadi", "Male")),
            new Book(2, "Java in 60 minutes", 250, new Author(2, "Vedu Shinde", "Female")),
            new Book(3, "Rich Dad, Poor Dad", 200, new Author(3, "Madhur Bhoyar", "Alpha Male"))
    );

    public static List<Book> getAllBook() {
        return books;
    }

    public static Book getBookById(Integer id) {
        return books.stream().filter(book -> book.id().equals(id))
                .findFirst().orElse(null);
    }

    public static Author getAuthorById(Integer id) {
        return books.stream()
                .map(Book::author)
                .filter(author -> author.id().equals(id))
                .findFirst().orElse(null);
    }
}
