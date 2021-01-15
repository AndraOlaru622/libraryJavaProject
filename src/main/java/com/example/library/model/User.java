package com.example.library.model;

import javax.validation.constraints.NotNull;

public class User {

    private int idUser;
    @NotNull(message = "Name cannot be null")
    private String name;
    @NotNull(message = "Email cannot be null")
    private String email;
    @NotNull(message = "Address cannot be null")
    private String address;



    public User(int idUser, String name, String email, String address) {
        this.idUser = idUser;
        this.name = name;
        this.email = email;
        this.address = address;
    }


    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
