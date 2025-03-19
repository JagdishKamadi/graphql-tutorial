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

import static org.junit.jupiter.api.Assertions.assertEquals;
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
    private static String query;

    @BeforeAll
    static void setUpObject() {
        author = new Author("Virat Kohli", "Male");
        book = new Book("King of Chase", 999, author);

        query = """
                query getBook($id: ID!)
                {
                   getBook(id: $id) {
                     name
                     price
                     author {
                       name
                       gender
                     }
                   }
                 }
                """;
    }

    @Test
    void testGetBook_BookFound() {
        int id = 1;
        when(bookService.getBookById(id)).thenReturn(book);

        graphQlTester.document(query)
                .variable("id", id)
                .execute()
                .path("getBook")
                .entity(Book.class)
                .satisfies((b) -> {
                    assertEquals("King of Chase", b.getName());
                    assertEquals(999, b.getPrice());
                    assertEquals("Virat Kohli", b.getAuthor().getName());
                    assertEquals("Male", b.getAuthor().getGender());
                });

        verify(bookService).getBookById(id);
    }
}