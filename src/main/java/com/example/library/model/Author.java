package com.example.library.model;

import javax.validation.constraints.NotNull;

public class Author {

    private int idAuthor;
    @NotNull(message = "Name cannot be null")
    private String name;

    public Author(int idAuthor, String name) {
        this.idAuthor = idAuthor;
        this.name = name;
    }

    public int getIdAuthor() {
        return idAuthor;
    }

    public void setIdAuthor(int idAuthor) {
        this.idAuthor = idAuthor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Author: " +
                "name=" + name;
    }
}
