package com.graphql.graphql_demo.service;

import com.graphql.graphql_demo.model.Author;
import com.graphql.graphql_demo.reposiory.AuthorRepository;
import exception.AuthorNotFoundException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class AuthorServiceTest {

    private static Author author1;
    private static Author author2;
    private static List<Author> authors;

    @InjectMocks
    private AuthorService authorService;

    @Mock
    private AuthorRepository authorRepository;


    @BeforeAll
    static void setUpObject() {
        author1 = new Author("James Bond", "Male");
        author2 = new Author("Steve Rogers", "Male");
        authors = Arrays.asList(author1, author2);
    }

    @Test
    void testGetAuthorById_AuthorFound() {
        when(authorRepository.findById(1)).thenReturn(Optional.of(author1));
        Author result = authorService.getAuthorById(1);
        assertNotNull(result);
        assertAll("Checking all fields", () -> assertEquals("James Bond", result.getName()), () -> assertEquals("Male", result.getGender()));
    }

    @Test
    void testGetAuthorById_AuthorNotFound() {
        when(authorRepository.findById(-1)).thenThrow(AuthorNotFoundException.class);
        assertThrows(AuthorNotFoundException.class, () -> authorService.getAuthorById(-1));
    }

    @Test
    void testSaveAuthor() {
        when(authorRepository.save(author2)).thenReturn(author2);
        Author result = authorService.saveAuthor(author2);
        assertNotNull(result);
        assertAll("Checking all fields", () -> assertEquals("Steve Rogers", result.getName()), () -> assertEquals("Male", result.getGender()));
    }

    @Test
    void testSaveAuthors() {
        when(authorRepository.saveAll(authors)).thenReturn(authors);
        List<Author> allAuthor = authorService.saveAuthors(authors);
        assertNotNull(allAuthor);
        assertAll("Checking all fields", () -> assertEquals("James Bond", allAuthor.get(0).getName()), () -> assertEquals("Male", allAuthor.get(0).getGender()), () -> assertEquals("Steve Rogers", allAuthor.get(1).getName()), () -> assertEquals("Male", allAuthor.get(1).getGender()));
    }
}