package com.example.library.model;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;


public class Book {


    private int idBook;
    @NotNull(message = "Id cannot be null")
    private int idAuthor;
    @NotNull(message = "Title cannot be null")
    private String title;
    @NotNull(message = "Number of copies cannot be null")
    private int nrCopies;

    public Book(int idBook, int idAuthor, String title, int nrCopies) {
        this.idBook = idBook;
        this.idAuthor = idAuthor;
        this.title = title;
        this.nrCopies = nrCopies;
    }



    public int getIdBook() {
        return idBook;
    }

    public void setIdBook(int idBook) {
        this.idBook = idBook;
    }

    public int getIdAuthor() {
        return idAuthor;
    }

    public void setIdAuthor(int idAuthor) {
        this.idAuthor = idAuthor;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getNrCopies() {
        return nrCopies;
    }

    public void setNrCopies(int nrCopies) {
        this.nrCopies = nrCopies;
    }

    @Override
    public String toString() {
        return "Book: " +
                "title=" + title;
    }
}
