package com.example.library.model;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class Borrowing {

    private int idBorrowing;
    @NotNull(message = "Id cannot be null")
    private int idUser;
    @NotNull(message = "Id cannot be null")
    private int idBook;
    @NotNull(message = "Date cannot be null")
    private String borrowDate;

    public Borrowing(int idBorrowing, int idUser, int idBook, String borrowDate) {
        this.idBorrowing = idBorrowing;
        this.idUser = idUser;
        this.idBook = idBook;
        this.borrowDate = borrowDate;
    }

    public int getIdBorrowing() {
        return idBorrowing;
    }

    public void setIdBorrowing(int idBorrowing) {
        this.idBorrowing = idBorrowing;
    }


    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdBook() {
        return idBook;
    }

    public void setIdBook(int idBook) {
        this.idBook = idBook;
    }

    public String getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(String borrowDate) {
        this.borrowDate = borrowDate;
    }
}
