package com.esliceu.PracticaDrawing2.Repos;

import com.esliceu.PracticaDrawing2.Entities.Draw;
import com.esliceu.PracticaDrawing2.Entities.Version;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class DrawRepoImpl implements DrawRepo {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public Draw saveDraw(Draw draw) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(
                "INSERT INTO draw (nameDraw, owner_id, creationDate) VALUES (?, ?, NOW())",
                new Object[]{draw.getNameDraw(), draw.getOwner_id()},
                keyHolder
        );

        draw.setId(keyHolder.getKey() != null ? keyHolder.getKey().intValue() : null);

        return draw;
    }



    @Override
    public void saveVersion(Version version) {
        jdbcTemplate.update("INSERT INTO version (id_draw, figures, modificationDate,id_user) VALUES (?, ?, NOW(),?)",
                version.getId_draw(),version.getFigures(),version.getId_user()
        );
    }

    /*
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
     */
}


