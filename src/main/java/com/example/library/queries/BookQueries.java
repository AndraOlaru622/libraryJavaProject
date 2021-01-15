package com.example.library.queries;

public class BookQueries {

    public final static String GET_BOOKS = "select * from books";
    public final static String GET_BOOK_BY_ID = "select * from books where idBook = ?";
    public final static String GET_BOOK_BY_TITLE = "select * from books where title = ?";
    public final static String ADD_BOOK  = "INSERT into books(idBook, idAuthor, title, nrCopies) values (null, ?, ?, ?)";
    public final static String GET_BOOK_AUTHOR =
            "SELECT books.idAuthor as idAuthor, name from books join authors on books.idAuthor = authors.idAuthor where title=?";
    public final static String INCREASE_NUMBER = "UPDATE BOOKS SET nrCopies = nrCopies + ? WHERE title=?";
    public final static String DELETE_BOOK = "DELETE FROM books WHERE title = ?";
}
