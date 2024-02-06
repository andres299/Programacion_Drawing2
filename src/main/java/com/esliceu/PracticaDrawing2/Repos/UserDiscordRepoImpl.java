package com.esliceu.PracticaDrawing2.Repos;

import com.esliceu.PracticaDrawing2.Entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserDiscordRepoImpl implements UserDiscordRepo{
    @Autowired
    JdbcTemplate jdbcTemplate;
    //Metodo para registrar usuario.
    @Override
    public void register(User user) {
        jdbcTemplate.update("INSERT INTO user (login,name,password,oauth) VALUES (?,?,?,?)",
                user.getLogin(), user.getName(), user.getPassword(), user.getOauth());
    }

    //Metodo por si existe el usuario.
    @Override
    public boolean existUser(String email) {
        String sql = "SELECT COUNT(*) FROM user WHERE login = ?";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, email);
        return count > 0;    }

    @Override
    public User user(String email) {
        String sql = "SELECT * FROM user WHERE login = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), email);
    }
}
