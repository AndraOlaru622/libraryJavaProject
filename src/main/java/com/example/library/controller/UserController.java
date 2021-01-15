package com.example.library.controller;

import com.example.library.exception.DuplicateException;
import com.example.library.exception.ObjectNotFoundException;
import com.example.library.model.User;
import com.example.library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping("/all")
    public List<User> getUsers() {
        return service.getUsers();
    }

    @GetMapping("/getByName")
    public ResponseEntity<?>getUserByName(@RequestParam String name) {
        try {
            return ResponseEntity.ok().body(service.getUserByName(name));
        } catch (ObjectNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }

    }

    @PostMapping("/add")
    public ResponseEntity<?> addUser(@RequestBody @Valid  User u) {

        try {
            User user = service.addNewUser(u);
            return ResponseEntity.created(URI.create("/books/" + user.getIdUser())).body(user);
        } catch (DuplicateException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
    }
}
