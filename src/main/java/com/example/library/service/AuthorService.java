package com.example.library.service;

import com.example.library.model.Author;
import com.example.library.model.Book;
import com.example.library.repository.AuthorRepository;
import com.example.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {

    @Autowired
    AuthorRepository repository;

    public List<Author> getAuthors() {
        return repository.getAuthors();
    }

    public Optional<Author> getAuthorByName(String name) {
        return repository.getAuthorByName(name);
    }

    public Author addNewAuthor(Author author) {

        return repository.addNewAuthor(author);
    }


    public List<Author> deleteAuthor(String name){

        return repository.deleteAuthor(name);
    }

}