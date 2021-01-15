package com.example.library.controller;

import com.example.library.exception.DuplicateException;
import com.example.library.exception.ObjectNotFoundException;
import com.example.library.model.Book;
import com.example.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService service;

    @GetMapping("/all")
    public List<Book> getBooks() {
        return service.getBooks();
    }

    @GetMapping("/getById")
    public ResponseEntity<?> getBookById(@RequestParam int id) {

        try {
            return ResponseEntity.ok().body(service.getBookById(id));
        } catch (ObjectNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }

    }

    @GetMapping("/getByTitle")
    public ResponseEntity<?>getBookByTitle(@RequestParam String title) {
        try {
            return ResponseEntity.ok().body(service.getBookByTitle(title));
        } catch (ObjectNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }

    }
    @GetMapping("/delete")
    public ResponseEntity<?> deleteBook(@RequestParam String title){
        try {
            return ResponseEntity.ok().body( service.deleteBook(title));

        } catch (ObjectNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }

    }

    @GetMapping("/getAuthorName")
    public ResponseEntity<?>getAuthorName(@RequestParam String title) {
        try {
            return ResponseEntity.ok().body(service.getAuthorName(title));
        } catch (ObjectNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }

    }



    @GetMapping("/increaseCount")
    public ResponseEntity<?>getAuthorName(@RequestParam String title, @RequestParam int nr) {
        try {
            return ResponseEntity.ok().body(service.increaseBookCount(title, nr));
        } catch (ObjectNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }

    }



    @PostMapping("/add")
    public ResponseEntity<?> addBook(@RequestBody @Valid Book b) {

        try {
            Book book = service.addNewBook(b);
            return ResponseEntity.created(URI.create("/books/" + book.getIdBook())).body(book);
        } catch (DuplicateException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
    }
}
