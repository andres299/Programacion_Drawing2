package com.esliceu.PracticaDrawing2.Entities;

public class permissions {
    private int id_draw;
    private int id_users;
    private int permissions;

    public permissions() {
    }

    public permissions(int id_draw, int id_users, int permissions) {
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

    public int getPermissions() {
        return permissions;
    }

    public void setPermissions(int permissions) {
        this.permissions = permissions;
    }
}
