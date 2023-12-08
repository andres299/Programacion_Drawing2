package com.esliceu.PracticaDrawing2.Repos;

import com.esliceu.PracticaDrawing2.Entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepoImpl implements UserRepo{
    @Autowired
    JdbcTemplate jdbcTemplate;

    //Metodo por si existe el usuario.
    @Override
    public boolean existUser(String login) {
        String sql = "SELECT COUNT(*) FROM user WHERE login = ?";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, login);
        return count > 0;    }
    //Metodo para registrar usuario.
    @Override
    public void register(User user) {
        jdbcTemplate.update("INSERT INTO user (login,name,password) VALUES (?,?,?)",
                user.getLogin(),user.getName(),user.getPassword());
    }
    //Metodo para iniciar sesion.
    @Override
    public boolean login(String login, String password) {
        String sql = "SELECT COUNT(*) FROM user WHERE login = ? AND password = ?";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, login, password);
        return count > 0;
    }
    //Metodo pra obtener informacion del usuario.
    @Override
    public User user(String login) {
        String sql = "SELECT * FROM user WHERE login = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), login);
    }
    //Metodo pra obtener una lista de usuarios.
    @Override
    public List<User> allUsers(int idUser) {
        String sql = "SELECT * FROM user WHERE id <> ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class), idUser);
    }

}
