package com.graphql.graphql_demo.controller;

import com.graphql.graphql_demo.model.Author;
import com.graphql.graphql_demo.model.Book;
import com.graphql.graphql_demo.service.AuthorService;
import com.graphql.graphql_demo.service.BookService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest;
import org.springframework.graphql.test.tester.GraphQlTester;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@GraphQlTest(BookController.class)
class BookControllerTest {

    @Autowired
    private GraphQlTester graphQlTester;

    @InjectMocks
    private BookController bookController;

    @MockitoBean
    private BookService bookService;

    @MockitoBean
    private AuthorService authorService;

    private static Book book;

    private static Author author;

    @BeforeAll
    static void setUpObject() {
        author = new Author("Virat Kohli", "Male");
        book = new Book("King of Chase", 999, author);
    }

    @Test
    void testGetBook_BookFound() {
        String query = """
                {
                   getBook(id: 1) {
                     name
                     price
                     author {
                       name
                       gender
                     }
                   }
                 }
                """;

        when(bookService.getBookById(1)).thenReturn(book);
        graphQlTester.document(query)
                .execute()
                .path("data.getBook.name")
                .entity(String.class)
                .isEqualTo("King of Chase")
                .path("data.getBook.price")
                .entity(Integer.class)
                .isEqualTo(999)
                .path("data.getBook.author.name")
                .entity(String.class)
                .isEqualTo("Virat Kohli")
                .path("data.getBook.author.gender")
                .entity(String.class)
                .isEqualTo("Male");

        verify(bookService).getBookById(1);
    }

}