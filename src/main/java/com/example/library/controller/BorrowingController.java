package com.example.library.controller;


import com.example.library.exception.DuplicateException;
import com.example.library.exception.ObjectNotFoundException;
import com.example.library.model.Author;
import com.example.library.model.Borrowing;
import com.example.library.service.BorrowingService;
import com.example.library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/borrowings")
public class BorrowingController {

    @Autowired
    private BorrowingService service;

    @GetMapping("/all")
    public ResponseEntity<?> getAll() {
        try {
            return ResponseEntity.ok().body(service.getAll());
        } catch (ObjectNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }

    }

    @PostMapping("/add")
    public ResponseEntity<?> addBorrowing(@RequestParam String user, String book) {

        try {
            Borrowing borrowing = service.addNewBorrowing(user, book);
            return ResponseEntity.created(URI.create("/borrowings/" + borrowing.getIdBorrowing())).body(borrowing);
        } catch (ObjectNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
    }

    @GetMapping("/getByUser")
    public ResponseEntity<?> getByUser(@RequestParam String user) {
        try {
            return ResponseEntity.ok().body(service.getByUser(user));
        } catch (ObjectNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }

    }

    @GetMapping("/getByBook")
    public ResponseEntity<?> getByBook(@RequestParam String book) {
        try {
            return ResponseEntity.ok().body(service.getByBook(book));
        } catch (ObjectNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }

    }

    @GetMapping("/delete")
    public ResponseEntity<?> deleteBorrowing(@RequestParam String user, String book){
        try {
            return ResponseEntity.ok().body( service.deleteBorrowing(user, book));

        } catch (ObjectNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }

    }
}
