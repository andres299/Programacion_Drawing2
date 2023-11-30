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

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO draw (nameDraw, owner_id, creationDate) VALUES (?, ?, NOW())",
                    Statement.RETURN_GENERATED_KEYS
            );

            ps.setString(1, draw.getNameDraw());
            ps.setInt(2, draw.getOwner_id());
            return ps;
        }, keyHolder);

        // Obtener el ID generado automáticamente
        Number key = keyHolder.getKey();

        if (key != null) {
            draw.setId(key.intValue());
        }
        return draw;
    }

    @Override
    public void saveVersion(Version version) {
        jdbcTemplate.update("INSERT INTO version (id_draw, figures, modificationDate,id_user) VALUES (?, ?, NOW(),?)",
                version.getId_draw(),version.getFigures(),version.getId_user()
        );
    }

    @Override
    public List<Draw> getDraws(int id) {
        return jdbcTemplate.query("SELECT * FROM draw WHERE owner_id = id", (resultSet, rowNum) -> {
            Draw draw = new Draw();
            draw.setId(resultSet.getInt("id"));
            draw.setNameDraw(resultSet.getString("nameDraw"));
            draw.setOwner_id(resultSet.getInt("owner_id"));
            draw.setCreationDate(resultSet.getString("creationDate"));
            draw.setVisualization(resultSet.getBoolean("visualization"));
            draw.setVisualization(resultSet.getBoolean("intTheTrash"));
            System.out.println("draw" + draw);
            return draw;
        });
    }

    @Override
    public Version VersionForDraw(int id) {
        String sql = "SELECT * FROM version WHERE id_draw = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, (resultSet, rowNum) -> {
            Version version = new Version();
            version.setId(resultSet.getInt("id"));
            version.setId_draw(resultSet.getInt("id_draw"));
            version.setFigures(resultSet.getString("figures"));
            version.setModificationDate(resultSet.getString("modificationDate"));
            version.setId_user(resultSet.getInt("id_user"));
            return version;
        });
    }
    /*

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


