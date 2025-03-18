package com.graphql.graphql_demo.controller;

import com.graphql.graphql_demo.model.Author;
import com.graphql.graphql_demo.model.Book;
import com.graphql.graphql_demo.service.AuthorService;
import com.graphql.graphql_demo.service.BookService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class BookController {
    private static final Logger LOGGER = LogManager.getLogger(BookController.class);

    private final BookService bookService;
    private final AuthorService authorService;

    public BookController(BookService bookService, AuthorService authorService) {
        this.bookService = bookService;
        this.authorService = authorService;
    }

    /**
     * method name should be the same name mention in schema.graphqls files
     */
    @QueryMapping
    public List<Book> getBooks() {
        List<Book> books = bookService.getAllBook();
        LOGGER.info("Getting all books {}", books);
        return books;
    }

    @QueryMapping
    public Book getBook(@Argument Integer id) {
        Book book = bookService.getBookById(id);
        LOGGER.info("Getting book {}", book);
        return book;
    }

    @MutationMapping
    public Book saveBook(@Argument String name, @Argument Integer price, @Argument String authorName, @Argument String authorGender) {
        Author author = new Author(authorName, authorGender);
        authorService.saveAuthor(author);
        Book book = new Book(name, price, author);
        Book book1 = bookService.saveBook(book);
        LOGGER.info("Saving  book {}", book1);
        return book1;
    }
}
