package com.graphql.graphql_demo.service;

import com.graphql.graphql_demo.model.Author;
import com.graphql.graphql_demo.model.Book;
import com.graphql.graphql_demo.reposiory.BookRepository;
import exception.BookNotFoundException;
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
class BookServiceTest {

    private static Book book1;
    private static Book book2;
    private static Author author1;
    private static Author author2;
    private static List<Book> books;

    @InjectMocks
    private BookService bookService;

    @Mock
    private BookRepository bookRepository;


    @BeforeAll
    static void setUpObject() {
        author1 = new Author("James Bond", "Male");
        author2 = new Author("Steve Rogers", "Male");
        book1 = new Book("Drilling in C++", 100, author1);
        book2 = new Book("Drilling in Java", 200, author2);
        books = Arrays.asList(book1, book2);
    }

    @Test
    void testGetAllBook() {
        when(bookRepository.findAll()).thenReturn(books);
        List<Book> allBook = bookService.getAllBook();
        assertNotNull(allBook);
        assertAll("Checking all fields",
                () -> assertEquals("Drilling in C++", allBook.get(0).getName()),
                () -> assertEquals(100, allBook.get(0).getPrice()),
                () -> assertEquals("James Bond", allBook.get(0).getAuthor().getName()),
                () -> assertEquals("Male", allBook.get(0).getAuthor().getGender()),
                () -> assertEquals("Drilling in Java", allBook.get(1).getName()),
                () -> assertEquals(200, allBook.get(1).getPrice()),
                () -> assertEquals("Steve Rogers", allBook.get(1).getAuthor().getName()),
                () -> assertEquals("Male", allBook.get(1).getAuthor().getGender())
        );
    }

    @Test
    void testGetBookById_BookFound() {
        when(bookRepository.findById(1)).thenReturn(Optional.of(book1));
        Book result = bookService.getBookById(1);
        assertNotNull(result);
        assertAll("Checking all fields",
                () -> assertEquals("Drilling in C++", result.getName()),
                () -> assertEquals(100, result.getPrice()),
                () -> assertEquals("James Bond", result.getAuthor().getName()),
                () -> assertEquals("Male", result.getAuthor().getGender())
        );
    }

    @Test
    void testGetBookById_BookNotFound() {
        when(bookRepository.findById(-1)).thenThrow(BookNotFoundException.class);
        assertThrows(BookNotFoundException.class, () -> bookService.getBookById(-1));
    }

    @Test
    void testSaveBook() {
        when(bookRepository.save(book2)).thenReturn(book2);
        Book result = bookService.saveBook(book2);
        assertNotNull(result);
        assertAll("Checking all fields",
                () -> assertEquals("Drilling in Java", result.getName()),
                () -> assertEquals(200, result.getPrice()),
                () -> assertEquals("Steve Rogers", result.getAuthor().getName()),
                () -> assertEquals("Male", result.getAuthor().getGender())
        );
    }

    @Test
    void testSaveBooks() {
        when(bookRepository.saveAll(books)).thenReturn(books);
        List<Book> allBook = bookService.saveBooks(books);
        assertNotNull(allBook);
        assertAll("Checking all fields",
                () -> assertEquals("Drilling in C++", allBook.get(0).getName()),
                () -> assertEquals(100, allBook.get(0).getPrice()),
                () -> assertEquals("James Bond", allBook.get(0).getAuthor().getName()),
                () -> assertEquals("Male", allBook.get(0).getAuthor().getGender()),
                () -> assertEquals("Drilling in Java", allBook.get(1).getName()),
                () -> assertEquals(200, allBook.get(1).getPrice()),
                () -> assertEquals("Steve Rogers", allBook.get(1).getAuthor().getName()),
                () -> assertEquals("Male", allBook.get(1).getAuthor().getGender())
        );
    }
}