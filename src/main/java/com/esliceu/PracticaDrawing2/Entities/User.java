package com.esliceu.PracticaDrawing2.Entities;

public class User {
    int id;
    String login;
    String name;
    String password;

    public User() {
    }

    public User(String login, String name, String password) {
        this.login = login;
        this.name = name;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
