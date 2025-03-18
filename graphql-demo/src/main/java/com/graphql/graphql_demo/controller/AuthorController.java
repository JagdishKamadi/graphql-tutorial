package com.graphql.graphql_demo.controller;

import com.graphql.graphql_demo.model.Author;
import com.graphql.graphql_demo.service.AuthorService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @QueryMapping
    public Author getAuthor(@Argument Integer id) {
        return authorService.getAuthorById(id);
    }
}
