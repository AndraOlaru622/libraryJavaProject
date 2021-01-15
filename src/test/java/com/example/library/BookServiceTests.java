package com.example.library;

import com.example.library.model.Book;
import com.example.library.repository.BookRepository;
import com.example.library.service.BookService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
public class BookServiceTests {

    @InjectMocks
    private BookService service;
    @Mock
    private BookRepository repository;

    @Test
    @DisplayName("Retrieve all books")
    public void getBooks() {

        //arrange
         when(repository.getBooks()).thenReturn(
                 Arrays.asList(new Book(1,1,"book5", 35))
         );
        //act
        List<Book> result = service.getBooks();
        //assert

        assertEquals(result.size(), 1);


    }

    @Test
    @DisplayName("Get book by title")
    public void getBookByTitle() {

        String title = "newBook";
        when(repository.getBookByTitle(title)).thenReturn(
                Optional.of(new Book(1,1,title, 35))
        );

        Optional<Book> result = service.getBookByTitle(title);

        assertEquals(result.get().getTitle(), title);

    }
    @Test
    @DisplayName("Add book")
    public void addNewBook() {

        String title = "newBook";
        Book b = new Book(1,1, "newBook", 20);
        when(repository.addNewBook(b)).thenReturn(
                new Book(1,1, "newBook", 20)
        );

        Book result = service.addNewBook(b);

        assertEquals(result.getTitle(), b.getTitle());
        assertEquals(result.getIdBook(), b.getIdBook());
        assertEquals(result.getIdAuthor(), b.getIdAuthor());
        assertEquals(result.getNrCopies(), b.getNrCopies());

    }



}
