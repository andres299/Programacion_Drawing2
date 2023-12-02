package com.esliceu.PracticaDrawing2.Repos;

import com.esliceu.PracticaDrawing2.Entities.User;

import java.util.List;

public interface UserRepo {
    boolean existUser(String login);

    void register(User user);

    boolean login(String login, String s);

    User user(String login);

    List<User> allUsers(int idUser);
}
