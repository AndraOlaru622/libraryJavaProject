package com.example.library.service;

import com.example.library.model.Author;
import com.example.library.model.Book;
import com.example.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    BookRepository repository;

    public List<Book> getBooks() {
        return repository.getBooks();
    }

    public List<Book> getBookById(int id) {
        return repository.getBookById(id);
    }

    public Optional<Book> getBookByTitle(String title) {

        return repository.getBookByTitle(title);

    }


    public Author getAuthorName(String title) {

        return repository.getAuthorName(title);

    }



    public Book addNewBook(Book book) {

        return repository.addNewBook(book);
    }

    public Optional<Book> increaseBookCount(String title, int nr){

        return repository.increaseBookCount(title,nr);
    }

    public List<Book> deleteBook(String title){

        return repository.deleteBook(title);
    }

}
