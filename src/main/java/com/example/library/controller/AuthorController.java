package com.example.library.controller;


import com.example.library.exception.DuplicateException;
import com.example.library.exception.ObjectNotFoundException;
import com.example.library.model.Author;
import com.example.library.model.Book;
import com.example.library.service.AuthorService;
import com.example.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    @Autowired
    private AuthorService service;

    @GetMapping("/all")
    public List<Author> getAuthors() {
        return service.getAuthors();
    }


    @GetMapping("/getByName")
    public ResponseEntity<?>getAuthorByName(@RequestParam String name) {
        try {
            return ResponseEntity.ok().body(service.getAuthorByName(name));
        } catch (ObjectNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }

    }
    @GetMapping("/delete")
    public ResponseEntity<?> deleteAuthor(@RequestParam String author){
        try {
            return ResponseEntity.ok().body( service.deleteAuthor(author));

        } catch (ObjectNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }

    }



    @PostMapping("/add")
    public ResponseEntity<?> addAuthor(@RequestBody @Valid Author a) {

        try {
            Author author = service.addNewAuthor(a);
            return ResponseEntity.created(URI.create("/authors/" + author.getIdAuthor())).body(author);
        } catch (DuplicateException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
    }
}