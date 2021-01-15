package com.example.library.queries;

public class AuthorQueries {

    public final static String GET_AUTHORS = "select * from authors";
    public final static String GET_AUTHOR_BY_NAME = "select * from AUTHORS where NAME = ?";
    public final static String ADD_AUTHOR  = "INSERT into AUTHORS(idAuthor, name) values (null, ?)";
    public final static String DELETE_AUTHOR = "DELETE FROM authors WHERE name = ?";
}
