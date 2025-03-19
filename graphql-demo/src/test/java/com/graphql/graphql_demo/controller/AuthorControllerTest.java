package com.graphql.graphql_demo.controller;

import com.graphql.graphql_demo.model.Author;
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

@GraphQlTest(value = AuthorController.class)
class AuthorControllerTest {

    @Autowired
    private GraphQlTester graphQlTester;

    @InjectMocks
    private AuthorController authorController;

    @MockitoBean
    private AuthorService authorService;

    @MockitoBean
    private BookService bookService;

    private static Author author;

    @BeforeAll
    static void setUpObject() {
        author = new Author("Virat Kohli", "Male");
    }

    @Test
    void testGetBook_BookFound() {
        int id = 1;
        String query = """
                query getAuthor($id: ID!)
                {
                   getAuthor(id: $id) {
                     name
                     gender
                   }
                 }
                """;

        when(authorService.getAuthorById(id)).thenReturn(author);

        graphQlTester.document(query)
                .variable("id", id)
                .execute()
                .path("getAuthor")
                .entity(Author.class)
                .satisfies(a -> {
                    assertEquals("Virat Kohli", a.getName());
                    assertEquals("Male", a.getGender());
                });

        verify(authorService).getAuthorById(id);
    }


}