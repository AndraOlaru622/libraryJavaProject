package com.example.library.service;

import com.example.library.model.Author;
import com.example.library.model.Book;
import com.example.library.model.Borrowing;
import com.example.library.repository.AuthorRepository;
import com.example.library.repository.BorrowingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BorrowingService {

    @Autowired
    BorrowingRepository repository;

    public List<Borrowing> getAll() {
        return repository.getBorrowings();
    }

    public List<Borrowing> getByUser(String user) {
        return repository.getBorrowingsByUser(user);
    }

    public List<Borrowing> getByBook(String title) {
        return repository.getBorrowingsByBook(title);
    }

    public Borrowing addNewBorrowing(String user, String book) {

        return repository.addNewBorrowing(user, book);
    }

    public List<Borrowing> deleteBorrowing(String user, String book){

        return repository.deleteBorrowing(user, book);
    }
}
