package com.example.library.repository;

import com.example.library.exception.DuplicateException;
import com.example.library.exception.ObjectNotFoundException;
import com.example.library.model.Author;
import com.example.library.model.Book;
import com.example.library.queries.BookQueries;
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
public class BookRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookRepository.class);
    private static final String TABLE_NAME = "BOOKS";
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Book> getBooks() {

        LOGGER.info("Retrieving books ... ");
        String actionDate = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date());
        jdbcTemplate.update(Audit.ADD_ACTION, TABLE_NAME, Audit.GET_ALL, actionDate);

        return jdbcTemplate.query(BookQueries.GET_BOOKS, (resultSet, i) -> new Book(
                resultSet.getInt("idBook"),
                resultSet.getInt("idAuthor"),
                resultSet.getString("title"),
                resultSet.getInt("nrCopies")
        ));


    }

    public List<Book> getBookById(int id) {

        LOGGER.info("Getting book... {}", id);
        String actionDate = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date());
        jdbcTemplate.update(Audit.ADD_ACTION, TABLE_NAME, Audit.GET_BY_ID, actionDate);

        return jdbcTemplate.query(BookQueries.GET_BOOK_BY_ID,  (resultSet, i) -> new Book(
                resultSet.getInt("idBook"),
                resultSet.getInt("idAuthor"),
                resultSet.getString("title"),
                resultSet.getInt("nrCopies")
        ), id);
    }

    public Optional<Book> getBookByTitle(String title) {

        LOGGER.info("Getting book... {}", title);
        String actionDate = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date());
        jdbcTemplate.update(Audit.ADD_ACTION, TABLE_NAME, Audit.GET_BY_NAME, actionDate);

        return jdbcTemplate.query(BookQueries.GET_BOOK_BY_TITLE,  (resultSet, i) -> new Book(
                resultSet.getInt("idBook"),
                resultSet.getInt("idAuthor"),
                resultSet.getString("title"),
                resultSet.getInt("nrCopies")
        ), title).stream().findFirst();


    }

    public Author getAuthorName(String title) {

        LOGGER.info("Getting author... {}", title);
        String actionDate = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date());
        jdbcTemplate.update(Audit.ADD_ACTION, "AUTHORS", Audit.GET_BY_NAME, actionDate);

        return jdbcTemplate.query(BookQueries.GET_BOOK_AUTHOR,  (resultSet, i) -> new Author(
                resultSet.getInt("idAuthor"),
                resultSet.getString("name")
        ), title).stream().findFirst()
                .orElseThrow(() -> new ObjectNotFoundException("Book title does not exist!!!"));

    }



    public Optional<Book> increaseBookCount(String title, int nr) {

        LOGGER.info("Adding copies... {}", title);
        String actionDate = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date());
        jdbcTemplate.update(Audit.ADD_ACTION, TABLE_NAME, Audit.INCREASE, actionDate);

        jdbcTemplate.update(BookQueries.INCREASE_NUMBER, nr, title);

        Optional<Book> bookUpdated = getBookByTitle(title);
        return bookUpdated;

    }




    public Book addNewBook(Book book) {

        LOGGER.info("Adding book... {}", book);
        String actionDate = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date());
        jdbcTemplate.update(Audit.ADD_ACTION, TABLE_NAME, Audit.ADDED, actionDate);

        Optional<Book> existingBook = getBookByTitle(book.getTitle());
        if(existingBook.isPresent()){

            throw new DuplicateException("Book already exists");

        }

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(BookQueries.ADD_BOOK, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1,book.getIdAuthor());
            preparedStatement.setString(2,book.getTitle());
            preparedStatement.setInt(3,book.getNrCopies());
            return preparedStatement;
        }, keyHolder);

        book.setIdBook(keyHolder.getKey().intValue());
        return book;
    }

    public List<Book> deleteBook(String title) {

        LOGGER.info("Delete book... {}", title);
        String actionDate = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date());
        jdbcTemplate.update(Audit.ADD_ACTION, TABLE_NAME, Audit.DELETED, actionDate);

        Optional<Book> existingBook = getBookByTitle(title);
        if(existingBook.isEmpty()){
            throw new ObjectNotFoundException("Book does not exist");
        }

        jdbcTemplate.update(BookQueries.DELETE_BOOK, title);

        return getBooks();

    }
}
