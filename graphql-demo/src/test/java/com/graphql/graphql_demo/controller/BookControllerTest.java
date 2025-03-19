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

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@GraphQlTest(value = BookController.class)
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
    private static List<Book> books;

    @BeforeAll
    static void setUpObject() {
        author = new Author("Virat Kohli", "Male");
        book = new Book("King of Chase", 999, author);
        Author author2 = new Author("MS Dhoni", "Alpha Male");
        Book book2 = new Book("Captain Cool", 1299, author2);
        books = Arrays.asList(book, book2);

    }

    @Test
    void testGetBooks() {
        String query = """
                {
                  getBooks {
                    name
                    price
                    author {
                      name
                      gender
                    }
                  }
                }
                """;
        when(bookService.getAllBook()).thenReturn(books);

        graphQlTester.document(query)
                .execute()
                .path("getBooks")
                .entityList(Book.class)
                .hasSize(2);

        verify(bookService).getAllBook();
    }

    @Test
    void testGetBooks_CheckingEachField() {
        String query = """
                {
                  getBooks {
                    name
                    price
                    author {
                      name
                      gender
                    }
                  }
                }
                """;
        when(bookService.getAllBook()).thenReturn(books);

        graphQlTester.document(query)
                .execute()
                .path("getBooks")
                .entityList(Book.class)
                .satisfies(b -> {
                    // checking for first book and author
                    assertEquals("King of Chase", b.get(0).getName());
                    assertEquals(999, b.get(0).getPrice());
                    assertEquals("Virat Kohli", b.get(0).getAuthor().getName());
                    assertEquals("Male", b.get(0).getAuthor().getGender());
                    // checking for second book and author
                    assertEquals("Captain Cool", b.get(1).getName());
                    assertEquals(1299, b.get(1).getPrice());
                    assertEquals("MS Dhoni", b.get(1).getAuthor().getName());
                    assertEquals("Alpha Male", b.get(1).getAuthor().getGender());
                });

        verify(bookService).getAllBook();
    }

    @Test
    void testGetBook_BookFound() {
        int id = 1;
        String query = """
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

        when(bookService.getBookById(id)).thenReturn(book);

        graphQlTester.document(query)
                .variable("id", id)
                .execute()
                .path("getBook")
                .entity(Book.class)
                .satisfies(b -> {
                    assertEquals("King of Chase", b.getName());
                    assertEquals(999, b.getPrice());
                    assertEquals("Virat Kohli", b.getAuthor().getName());
                    assertEquals("Male", b.getAuthor().getGender());
                });

        verify(bookService).getBookById(id);
    }
}