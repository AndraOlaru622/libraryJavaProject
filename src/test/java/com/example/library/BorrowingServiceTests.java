package com.example.library;

import com.example.library.model.Book;
import com.example.library.model.Borrowing;
import com.example.library.model.User;
import com.example.library.repository.BookRepository;
import com.example.library.repository.BorrowingRepository;
import com.example.library.service.BookService;
import com.example.library.service.BorrowingService;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BorrowingServiceTests {

    @InjectMocks
    private BorrowingService service;
    @Mock
    private BorrowingRepository repository;

    @Test
    @DisplayName("Retrieve all borrowings")
    public void getBorrowings() {

        //arrange
        when(repository.getBorrowings()).thenReturn(
                Arrays.asList(new Borrowing(1,3,6,"2021-01-14"))
        );
        //act
        List<Borrowing> result = service.getAll();
        //assert

        assertEquals(result.size(), 1);


    }

    @Test
    @DisplayName("Add borrowing")
    public void addBorrowing() {

        User u = new User(2,"user1", "user1@gmail.com", "add1");
        Book b = new Book(1,5,"book1", 10);

        String userName = "user1";
        String title = "book1";

        when(repository.addNewBorrowing(userName, title)).thenReturn(
                new Borrowing(1,2,1,"2021-01-14")
        );


        Borrowing result = service.addNewBorrowing(userName, title);
        assertEquals(result.getIdBook(), 1);
        assertEquals(result.getIdUser(), 2);


    }

    @Test
    @DisplayName("Get borrowings for user")
    public void getByUser() {

        String userName = "name1";
        when(repository.getBorrowingsByUser(userName)).thenReturn(
                Arrays.asList(
                        new Borrowing(1,1,3, "2021-01-12"),
                        new Borrowing(2,1,10, "2021-01-12"),
                        new Borrowing(3,1,12, "2021-01-12")
                )
        );

        List<Borrowing> result = service.getByUser(userName);

        assertEquals(result.size(), 3);
        assertEquals(result.get(0).getIdUser(), 1);
        assertEquals(result.get(1).getIdUser(), 1);
        assertEquals(result.get(2).getIdUser(), 1);

    }

    @Test
    @DisplayName("Get borrowings by book title")
    public void getByBook() {

        String title = "book1";
        when(repository.getBorrowingsByBook(title)).thenReturn(
                Arrays.asList(
                        new Borrowing(1,3,4, "2021-01-12"),
                        new Borrowing(2,4,4, "2021-01-12"),
                        new Borrowing(3,1,4, "2021-01-12")
                )
        );

        List<Borrowing> result = service.getByBook(title);

        assertEquals(result.size(), 3);
        assertEquals(result.get(0).getIdBook(), 4);
        assertEquals(result.get(1).getIdBook(), 4);
        assertEquals(result.get(2).getIdBook(), 4);

    }


}
