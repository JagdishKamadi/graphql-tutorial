package com.graphql.graphql_demo.controller;

import com.graphql.graphql_demo.model.Book;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class BookController {
    private static final Logger LOGGER = LogManager.getLogger(BookController.class);

    /**
     * method name should be the same name mention in schema.graphqls files
     */
    @QueryMapping
    public List<Book> getAllBook() {
        List<Book> books = Book.getBooks();
        LOGGER.info("Getting all books {}", books);
        return books;
    }
}
