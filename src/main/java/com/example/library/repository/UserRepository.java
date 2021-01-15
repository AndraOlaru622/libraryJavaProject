package com.example.library.repository;

import com.example.library.model.Book;
import com.example.library.model.User;
import com.example.library.queries.BookQueries;
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
public class UserRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserRepository.class);
    private static final String TABLE_NAME = "USERS";

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private Audit audit = new Audit();

    public List<User> getUsers() {

        List<User> users = jdbcTemplate.query(UserQueries.GET_USERS, (resultSet, i) -> new User(
                resultSet.getInt("idUser"),
                resultSet.getString("name"),
                resultSet.getString("email"),
                resultSet.getString("address")
        ));


        String actionDate = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        LOGGER.info("Retrieving users ...");
        jdbcTemplate.update(Audit.ADD_ACTION, TABLE_NAME, Audit.GET_ALL, actionDate);

        return users;

    }

    public Optional<User> getUserByName(String name) {


        LOGGER.info("Getting user... {}", name);
        String actionDate = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date());
        jdbcTemplate.update(Audit.ADD_ACTION, TABLE_NAME, Audit.GET_BY_NAME, actionDate);

        return jdbcTemplate.query(UserQueries.GET_USER_BY_NAME,  (resultSet, i) -> new User(
                resultSet.getInt("idUser"),
                resultSet.getString("name"),
                resultSet.getString("email"),
                resultSet.getString("address")
        ), name).stream().findFirst();

    }


    public User addUser(User user) {

        LOGGER.info("Adding user... {}", user.getName());
        String actionDate = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        jdbcTemplate.update(Audit.ADD_ACTION, TABLE_NAME, Audit.ADDED, actionDate);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(UserQueries.ADD_USER, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1,user.getName());
            preparedStatement.setString(2,user.getEmail());
            preparedStatement.setString(3,user.getAddress());
            return preparedStatement;
        }, keyHolder);

        user.setIdUser(keyHolder.getKey().intValue());
        return user;
    }


}
