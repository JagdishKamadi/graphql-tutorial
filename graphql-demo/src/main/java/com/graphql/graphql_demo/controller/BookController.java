package com.graphql.graphql_demo.controller;

import com.graphql.graphql_demo.model.Author;
import com.graphql.graphql_demo.model.Book;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Random;

@Controller
public class BookController {
    private static final Logger LOGGER = LogManager.getLogger(BookController.class);

    /**
     * method name should be the same name mention in schema.graphqls files
     */
    @QueryMapping
    public List<Book> getBooks() {
        List<Book> books = Book.getAllBook();
        LOGGER.info("Getting all books {}", books);
        return books;
    }

    @QueryMapping
    public Book getBook(@Argument Integer id) {
        Book book = Book.getBookById(id);
        LOGGER.info("Getting  book {}", book);
        return book;
    }

    @QueryMapping
    public Author getAuthor(@Argument Integer id) {
        Author author = Book.getAuthorById(id);
        LOGGER.info("Getting  author {}", author);
        return author;
    }

    @MutationMapping
    public Book saveBook(@Argument String name, @Argument Integer price) {
        int id = Math.round(new Random().nextInt());
        Book book = new Book(id, name, price, null);
        LOGGER.info("Saving  book {}", book);
        return book;
    }
}
