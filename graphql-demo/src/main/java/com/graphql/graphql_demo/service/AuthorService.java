package com.graphql.graphql_demo.service;

import com.graphql.graphql_demo.model.Author;
import com.graphql.graphql_demo.reposiory.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public Author getAuthorById(Integer id) {
        return authorRepository.findById(id).orElseThrow(() -> new RuntimeException("Author doesn't exist with id " + id));
    }

    public Author saveAuthor(Author author) {
        return authorRepository.save(author);
    }

    public List<Author> saveAuthors(List<Author> authorList) {
        return authorRepository.saveAll(authorList);
    }
}
