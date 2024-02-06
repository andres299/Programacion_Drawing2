package com.esliceu.PracticaDrawing2.Repos;

import com.esliceu.PracticaDrawing2.Entities.User;

public interface UserDiscordRepo {
    boolean existUser(String email);

    void register(User user);

    User user(String email);
}
