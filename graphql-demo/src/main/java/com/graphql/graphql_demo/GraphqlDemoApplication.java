package com.graphql.graphql_demo;

import com.graphql.graphql_demo.model.Author;
import com.graphql.graphql_demo.model.Book;
import com.graphql.graphql_demo.service.AuthorService;
import com.graphql.graphql_demo.service.BookService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class GraphqlDemoApplication implements CommandLineRunner {

    private final BookService bookService;
    private final AuthorService authorService;

    public GraphqlDemoApplication(BookService bookService, AuthorService authorService) {
        this.bookService = bookService;
        this.authorService = authorService;
    }

    public static void main(String[] args) {
        SpringApplication.run(GraphqlDemoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        Author author1 = new Author("Navneet Rana", "female");
        Author author2 = new Author("Sindhu Sing", "Male");
        Author author3 = new Author("Virat Kohli", "Male");
        List<Author> authors = Arrays.asList(author1, author2, author3);
        authorService.saveAuthors(authors);

        List<Book> books = Arrays.asList(
                new Book("Drilling in C++", 100, author1),
                new Book("Drilling in Java++", 110, author2),
                new Book("Drilling in Python", 120, author3)
        );
        bookService.saveBooks(books);

    }
}
