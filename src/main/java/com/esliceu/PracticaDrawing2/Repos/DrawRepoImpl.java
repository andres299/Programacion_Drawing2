package com.esliceu.PracticaDrawing2.Repos;

import com.esliceu.PracticaDrawing2.Entities.Draw;
import com.esliceu.PracticaDrawing2.Entities.Figure;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Repository
public class DrawRepoImpl implements DrawRepo {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public void saveDraw(Draw draw) {
        jdbcTemplate.update("INSERT INTO Draw (name, creationDate, modificationDate, figures, createdByUser)" +
                        "VALUES (?, ?, ?, ?, ?)", draw.getName(), draw.getCreationDate(),
                    draw.getModificationDate(), draw.getFigures(), draw.getCreatedByUser());
    }


    @Override
    public List<Draw> getDraws() {
        String selectSql = "SELECT * FROM Draw";
        return jdbcTemplate.query(selectSql, new BeanPropertyRowMapper<>(Draw.class));
    }

    @Override
    public void deleteDraw(int id, String login) {
        jdbcTemplate.update("DELETE FROM Draw WHERE id = ? AND createdByUser = ?", id, login);
    }

    @Override
    public List<Draw> getDrawByUser(String login) {
        return jdbcTemplate.query("SELECT * FROM Draw WHERE createdByUser = ?",
                new Object[]{login}, new BeanPropertyRowMapper<>(Draw.class));
    }

    @Override
    public List<Figure> getFiguresByDrawId(int drawId) {
        return jdbcTemplate.query("SELECT * FROM Figure WHERE drawId = ?",
                new Object[]{drawId}, new BeanPropertyRowMapper<>(Figure.class));
    }

    @Override
    public Draw getDrawById(int id) {
        return jdbcTemplate.queryForObject("SELECT * FROM Draw WHERE id = ?",
                new Object[]{id}, new BeanPropertyRowMapper<>(Draw.class));
    }

    @Override
    public void updateDraw(Draw existDraw) {
        jdbcTemplate.update("UPDATE Draw SET name = ?, creationDate = ?, modificationDate = ?, figures = ?, createdByUser = ? WHERE id = ?",
                existDraw.getName(), existDraw.getCreationDate(), existDraw.getModificationDate(),
                existDraw.getFigures(), existDraw.getCreatedByUser(), existDraw.getId());
    }
}


