package com.esliceu.PracticaDrawing2.Repos;

import com.esliceu.PracticaDrawing2.DTO.DrawWithVersionDTO;
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

    //Metodo para guardar el dibujo(draw) y obtener la id generada en la BBDD.
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

    //Metodo para guardar la version del dibujo.
    @Override
    public void saveVersion(Version version) {
        jdbcTemplate.update("INSERT INTO version (id_draw, figures, modificationDate,id_user) VALUES (?, ?, NOW(),?)",
                version.getId_draw(),version.getFigures(),version.getId_user()
        );
    }

    //Metodo para mostrar las imagenes
    @Override
    public List<DrawWithVersionDTO> getDraws(int id) {
        String sql = "SELECT draw.*, version.figures, version.modificationDate FROM draw JOIN version ON " +
                "draw.id = version.id_draw WHERE (draw.visualization = 1 AND draw.intTheTrash = 0) " +
                "OR (draw.owner_id = ? AND draw.intTheTrash = 0)";
        List<DrawWithVersionDTO> allDrawWhithVersion = jdbcTemplate.query(sql,
                new BeanPropertyRowMapper<>(DrawWithVersionDTO.class),id);
        return allDrawWhithVersion;
    }

    // Método para actualizar el campo intTheTrash en la tabla draw
    @Override
    public void updateDraw(int id, int id_user) {
        // Aquí verificamos que el id_user es el propietario de la imagen antes de realizar la actualización
        String sql = "UPDATE draw SET intTheTrash = 1 WHERE id = ? AND owner_id = ?";
        jdbcTemplate.update(sql, id, id_user);
    }

    @Override
    public List<DrawWithVersionDTO> getDrawsTrash(int id) {
        String sql = "SELECT draw.*, version.figures, version.modificationDate FROM draw JOIN version ON " +
                "draw.id = version.id_draw WHERE (draw.visualization = 1 AND draw.intTheTrash = 1) " +
                "OR (draw.owner_id = ? AND draw.intTheTrash = 1)";
        List<DrawWithVersionDTO> allDrawWhithVersion = jdbcTemplate.query(sql,
                new BeanPropertyRowMapper<>(DrawWithVersionDTO.class),id);
        return allDrawWhithVersion;    }
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


