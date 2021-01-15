package com.example.library.queries;

public class BorrowingQueries {

    public final static String GET_BORROWINGS = "select * from BORROWINGS";
    public final static String GET_BY_USER = "select * from BORROWINGS where idUser = ?";
    public final static String GET_BY_BOOK = "select * from BORROWINGS where idBook = ?";
    public final static String NEW = "INSERT into BORROWINGS(idBorrowing, idUser, idBook, borrowDate) values (null, ?, ?, ?)";
    public final static String DELETE_BORROWING = "DELETE FROM BORROWINGS WHERE idUser = ? and idBook = ?";
}
