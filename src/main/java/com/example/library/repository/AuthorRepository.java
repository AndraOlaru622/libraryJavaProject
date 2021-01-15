package com.example.library.repository;

import com.example.library.exception.DuplicateException;
import com.example.library.exception.ObjectNotFoundException;
import com.example.library.model.Author;
import com.example.library.model.Book;
import com.example.library.queries.AuthorQueries;
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
public class AuthorRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorRepository.class);
    private static final String TABLE_NAME = "AUTHORS";
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Author> getAuthors() {

        LOGGER.info("Retrieving authors ... ");
        String actionDate = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date());
        jdbcTemplate.update(Audit.ADD_ACTION, TABLE_NAME, Audit.GET_ALL, actionDate);

        return jdbcTemplate.query(AuthorQueries.GET_AUTHORS, (resultSet, i) -> new Author(
                resultSet.getInt("idAuthor"),
                resultSet.getString("name")
        ));


    }



    public Optional<Author> getAuthorByName(String name) {

        LOGGER.info("Getting author... {}", name);
        String actionDate = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date());
        jdbcTemplate.update(Audit.ADD_ACTION, TABLE_NAME, Audit.GET_BY_NAME, actionDate);

        return jdbcTemplate.query(AuthorQueries.GET_AUTHOR_BY_NAME,(resultSet, i) -> new Author(
                resultSet.getInt("idAuthor"),
                resultSet.getString("name")
        ), name).stream().findFirst();


    }

    public Author addNewAuthor(Author author) {

        LOGGER.info("Adding author... {}", author);
        String actionDate = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date());
        jdbcTemplate.update(Audit.ADD_ACTION, TABLE_NAME, Audit.ADDED, actionDate);

        Optional<Author> existingAuthor = getAuthorByName(author.getName());
        if(existingAuthor.isPresent()){

            throw new DuplicateException("The author already exists");

        }

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(AuthorQueries.ADD_AUTHOR, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1,author.getName());
            return preparedStatement;
        }, keyHolder);

        author.setIdAuthor(keyHolder.getKey().intValue());
        return author;
    }

    public List<Author> deleteAuthor(String name) {

        LOGGER.info("Delete author... {}", name);
        String actionDate = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date());
        jdbcTemplate.update(Audit.ADD_ACTION, TABLE_NAME, Audit.DELETED, actionDate);
        Optional<Author> existingAuthor = getAuthorByName(name);
        if(existingAuthor.isEmpty()){
            throw new ObjectNotFoundException("Author does not exist");
        }

        jdbcTemplate.update(AuthorQueries.DELETE_AUTHOR, name);

        return getAuthors();

    }
}
