package com.esliceu.PracticaDrawing2.Repos;

import com.esliceu.PracticaDrawing2.Entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepoImpl implements UserRepo{
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public boolean existUser(String login) {
        String sql = "SELECT COUNT(*) FROM user WHERE login = ?";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, login);
        return count > 0;    }

    @Override
    public void register(User user) {
        jdbcTemplate.update("INSERT INTO user (login,name,password) VALUES (?,?,?)",
                user.getLogin(),user.getName(),user.getPassword());
    }

    @Override
    public boolean login(String login, String password) {
        String sql = "SELECT COUNT(*) FROM user WHERE login = ? AND password = ?";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, login, password);
        return count > 0;
    }
}
