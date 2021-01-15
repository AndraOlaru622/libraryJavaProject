package com.example.library.queries;

public class UserQueries {

    public final static String GET_USERS = "select * from users";
    public final static String ADD_USER  = "INSERT into users(idUser, name, email, address) values (null, ?, ?, ?)";
    public final static String DELETE_USER = "DELETE FROM users WHERE name = ?";
    public final static String GET_USER_BY_NAME = "select * from users where name = ?";

}
