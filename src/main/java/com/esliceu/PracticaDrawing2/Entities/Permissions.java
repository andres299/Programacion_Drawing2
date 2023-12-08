package com.esliceu.PracticaDrawing2.Entities;

public class Permissions {
    private int id_draw;
    private int id_users;
    private String permissions;
    private String in_your_trash;

    public Permissions() {
    }

    public Permissions(int id_draw, int id_users, String permissions) {
        this.id_draw = id_draw;
        this.id_users = id_users;
        this.permissions = permissions;
    }

    public int getId_draw() {
        return id_draw;
    }

    public void setId_draw(int id_draw) {
        this.id_draw = id_draw;
    }

    public int getId_users() {
        return id_users;
    }

    public void setId_users(int id_users) {
        this.id_users = id_users;
    }

    public String getPermissions() {
        return permissions;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }

    public String getIn_your_trash() {
        return in_your_trash;
    }

    public void setIn_your_trash(String in_your_trash) {
        this.in_your_trash = in_your_trash;
    }
}
