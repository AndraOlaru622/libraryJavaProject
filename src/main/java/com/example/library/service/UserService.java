package com.example.library.service;

import com.example.library.model.Book;
import com.example.library.model.User;
import com.example.library.repository.BookRepository;
import com.example.library.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository repository;

    public List<User> getUsers() {
        return repository.getUsers();
    }

    public User addNewUser(User user) {

        return repository.addUser(user);
    }

    public Optional<User> getUserByName(String name) {

        return repository.getUserByName(name);
    }

}
