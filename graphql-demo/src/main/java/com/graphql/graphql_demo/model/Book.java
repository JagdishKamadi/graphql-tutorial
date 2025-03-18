package com.graphql.graphql_demo.model;

import java.util.Arrays;
import java.util.List;

public record Book(Integer id, String name, Integer price) {

    private static List<Book> books = Arrays.asList(
            new Book(1, "Drilling in c", 100),
            new Book(2, "Java in 60 minutes", 250),
            new Book(3, "Rich Dad, Poor Dad", 200)
    );

    public static List<Book> getBooks() {
        return books;
    }
}
