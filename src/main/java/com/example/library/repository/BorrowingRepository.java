package com.example.library.repository;


import com.example.library.exception.DuplicateException;
import com.example.library.exception.ObjectNotFoundException;
import com.example.library.model.Author;
import com.example.library.model.Book;
import com.example.library.model.Borrowing;
import com.example.library.model.User;
import com.example.library.queries.AuthorQueries;
import com.example.library.queries.BookQueries;
import com.example.library.queries.BorrowingQueries;
import com.example.library.queries.UserQueries;
import com.example.library.service.audit.Audit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public class BorrowingRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(BorrowingRepository.class);
    private static final String TABLE_NAME = "BORROWINGS";
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Borrowing> getBorrowings() {

        LOGGER.info("Retrieving borrowings ... ");
        String actionDate = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        jdbcTemplate.update(Audit.ADD_ACTION, TABLE_NAME, Audit.GET_ALL, actionDate);

        return jdbcTemplate.query(BorrowingQueries.GET_BORROWINGS, (resultSet, i) -> new Borrowing(
                resultSet.getInt("idBorrowing"),
                resultSet.getInt("idUser"),
                resultSet.getInt("idBook"),
                resultSet.getString("borrowDate")
        ));


    }


    public List<Borrowing> getBorrowingsByUser(String userName) {

        LOGGER.info("Retrieving borrowings for {}", userName);
        String actionDate = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        jdbcTemplate.update(Audit.ADD_ACTION, TABLE_NAME, Audit.GET_BY_NAME, actionDate);

        Optional<User> user = jdbcTemplate.query(UserQueries.GET_USER_BY_NAME,  (resultSet, i) -> new User(
                resultSet.getInt("idUser"),
                resultSet.getString("name"),
                resultSet.getString("email"),
                resultSet.getString("address")
        ), userName).stream().findFirst();

        if(user.isEmpty()) {

            throw new ObjectNotFoundException("User does not exist");
        }


        return jdbcTemplate.query(BorrowingQueries.GET_BY_USER, (resultSet, i) -> new Borrowing(
                resultSet.getInt("idBorrowing"),
                resultSet.getInt("idUser"),
                resultSet.getInt("idBook"),
                resultSet.getString("borrowDate")
        ), user.get().getIdUser());


    }


    public List<Borrowing> getBorrowingsByBook(String title) {

        LOGGER.info("Retrieving borrowings for {}", title);
        String actionDate = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        jdbcTemplate.update(Audit.ADD_ACTION, TABLE_NAME, Audit.GET_BY_NAME, actionDate);

        Optional<Book> book = jdbcTemplate.query(BookQueries.GET_BOOK_BY_TITLE,  (resultSet, i) -> new Book(
                resultSet.getInt("idBook"),
                resultSet.getInt("idAuthor"),
                resultSet.getString("title"),
                resultSet.getInt("nrCopies")
        ), title).stream().findFirst();

        if(book.isEmpty()) {

            throw new ObjectNotFoundException("Book does not exist");
        }




        return jdbcTemplate.query(BorrowingQueries.GET_BY_BOOK, (resultSet, i) -> new Borrowing(
                resultSet.getInt("idBorrowing"),
                resultSet.getInt("idUser"),
                resultSet.getInt("idBook"),
                resultSet.getString("borrowDate")
        ), book.get().getIdBook());


    }

    public Borrowing addNewBorrowing(String userName, String bookTitle) {

        LOGGER.info("Adding borrowing...");

        Optional<Book> book = getBook(bookTitle);

        Optional<User> user = getUser(userName);

        if(book.isEmpty()) {

            throw new ObjectNotFoundException("Book does not exist");
        }

        if(user.isEmpty()) {

            throw new ObjectNotFoundException("User does not exist");
        }

        String actionDate = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date());
        jdbcTemplate.update(Audit.ADD_ACTION, TABLE_NAME, Audit.ADDED, actionDate);

        String borrowDate = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(BorrowingQueries.NEW, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1,user.get().getIdUser());
            preparedStatement.setInt(2,book.get().getIdBook());
            preparedStatement.setString(3, borrowDate );
            return preparedStatement;
        }, keyHolder);

        jdbcTemplate.update(BookQueries.INCREASE_NUMBER, -1, book.get().getTitle());

        Borrowing b = new Borrowing(keyHolder.getKey().intValue(),user.get().getIdUser(), book.get().getIdBook(), borrowDate);
        return b;
    }

    public List<Borrowing> deleteBorrowing(String userName, String bookTitle) {


        LOGGER.info("Delete borrowing...");
        Optional<Book> book = jdbcTemplate.query(BookQueries.GET_BOOK_BY_TITLE,  (resultSet, i) -> new Book(
                resultSet.getInt("idBook"),
                resultSet.getInt("idAuthor"),
                resultSet.getString("title"),
                resultSet.getInt("nrCopies")
        ), bookTitle).stream().findFirst();

        Optional<User> user = jdbcTemplate.query(UserQueries.GET_USER_BY_NAME,  (resultSet, i) -> new User(
                resultSet.getInt("idUser"),
                resultSet.getString("name"),
                resultSet.getString("email"),
                resultSet.getString("address")
        ), userName).stream().findFirst();

        if(book.isEmpty()) {

            throw new ObjectNotFoundException("Book does not exist");
        }

        if(user.isEmpty()) {

            throw new ObjectNotFoundException("User does not exist");
        }

        String actionDate = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date());
        jdbcTemplate.update(Audit.ADD_ACTION, TABLE_NAME, Audit.DELETED, actionDate);


        jdbcTemplate.update(BorrowingQueries.DELETE_BORROWING, user.get().getIdUser(), book.get().getIdBook());

        jdbcTemplate.update(BookQueries.INCREASE_NUMBER, 1, book.get().getTitle());
        return getBorrowings();

    }

    public Optional<Book> getBook(String title) {

        Optional<Book> book = jdbcTemplate.query(BookQueries.GET_BOOK_BY_TITLE,  (resultSet, i) -> new Book(

                resultSet.getInt("idBook"),
                resultSet.getInt("idAuthor"),
                resultSet.getString("title"),
                resultSet.getInt("nrCopies")
        ), title).stream().findFirst();


        return book;

    }

    public Optional<User> getUser(String userName) {

        Optional<User> user = jdbcTemplate.query(UserQueries.GET_USER_BY_NAME,  (resultSet, i) -> new User(

                resultSet.getInt("idUser"),
                resultSet.getString("name"),
                resultSet.getString("email"),
                resultSet.getString("address")

        ), userName).stream().findFirst();


        return user;

    }


}
